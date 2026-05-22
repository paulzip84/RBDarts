import { summarizeStandalone, validateScore, type ParticipantScorecard } from "./domain/scoringRules.ts";
import { requireAuth, requireNonEmpty, requireScore } from "./trustedOperations.ts";
import type { CallableRequest } from "firebase-functions/v2/https";

export interface CreateStandaloneGameInput {
  gameName: string;
  participants: Array<{ participantId: string; displayName: string }>;
  regulationInnings?: number;
  extraInningsEnabled?: boolean;
}

export function createStandaloneGame(
  request: CallableRequest<CreateStandaloneGameInput>
): { standaloneGameId: string; participantCount: number } {
  const auth = requireAuth(request);
  const gameName = requireNonEmpty(request.data.gameName, "Game name");
  const participants = request.data.participants ?? [];
  if (participants.length < 1) {
    throw new Error("At least one participant is required.");
  }
  for (const participant of participants) {
    requireNonEmpty(participant.participantId, "Participant id");
    requireNonEmpty(participant.displayName, "Participant name");
  }
  return {
    standaloneGameId: `standalone-${auth.uid}-${Date.now()}`,
    participantCount: participants.length
  };
}

export interface SubmitStandaloneScoreInput {
  participantId: string;
  inningNumber: number;
  score: number;
}

export function submitStandaloneScore(request: CallableRequest<SubmitStandaloneScoreInput>): {
  participantId: string;
  inningNumber: number;
  score: number;
} {
  requireAuth(request);
  const score = requireScore(request.data.score);
  if (!Number.isInteger(request.data.inningNumber) || request.data.inningNumber <= 0) {
    throw new Error("Inning number must be positive.");
  }
  return {
    participantId: requireNonEmpty(request.data.participantId, "Participant id"),
    inningNumber: request.data.inningNumber,
    score
  };
}

export function completeStandaloneGame(
  participants: ParticipantScorecard[],
  regulationInnings = 9,
  extraInningsEnabled = true
) {
  participants.forEach((participant) => {
    Object.values(participant.scoresByInning).forEach(validateScore);
  });
  return summarizeStandalone(participants, regulationInnings, extraInningsEnabled);
}
