import { calculateHandicap } from "./domain/scoringRules.ts";
import type { HandicapRoundingRule } from "./domain/types.ts";
import type { CallableRequest } from "firebase-functions/v2/https";
import { requireAuth, requireNonEmpty } from "./trustedOperations.ts";

export interface CreateMatchInput {
  leagueId: string;
  homeTeamId: string;
  awayTeamId: string;
}

export function createMatch(request: CallableRequest<CreateMatchInput>) {
  requireAuth(request);
  const leagueId = requireNonEmpty(request.data.leagueId, "League id");
  const homeTeamId = requireNonEmpty(request.data.homeTeamId, "Home team id");
  const awayTeamId = requireNonEmpty(request.data.awayTeamId, "Away team id");
  if (homeTeamId === awayTeamId) {
    throw new Error("Home and away teams must be different.");
  }
  return {
    matchId: `match-${Date.now()}`,
    leagueId,
    homeTeamId,
    awayTeamId,
    status: "scheduled" as const
  };
}

export function calculateGameHandicap(
  teamAverageSums: Record<string, number>,
  handicapPercent: number,
  roundingRule: HandicapRoundingRule
) {
  return calculateHandicap(teamAverageSums, handicapPercent, roundingRule);
}
