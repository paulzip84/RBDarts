import { randomUUID } from "node:crypto";
import type {
  CorrectionAuditRecord,
  Game,
  HandicapRoundingRule,
  InningScore,
  LeagueRole,
  PracticeAttempt
} from "./types.ts";

export interface ParticipantScorecard {
  participantId: string;
  displayName: string;
  scoresByInning: Record<number, number>;
  average?: number;
}

export interface ParticipantSummary {
  participantId: string;
  displayName: string;
  total: number;
  zeroCount: number;
  nineCount: number;
  performanceVersusAverage?: number;
}

export interface GameSummary {
  participantSummaries: ParticipantSummary[];
  winnerIds: string[];
  margin: number;
  inningsPlayed: number;
  needsExtraInning: boolean;
}

export interface HandicapResult {
  lowerAverageTeamId?: string;
  higherAverageTeamId?: string;
  handicap: number;
}

export interface PracticeStats {
  attempts: number;
  average: number;
  bestScore: number;
  zeroCount: number;
  nineCount: number;
  recentTrend: number;
}

export interface TeamStanding {
  teamId: string;
  leaguePoints: number;
  gamesWon: number;
  pointDifferential: number;
  totalAdjustedScore: number;
  totalRawScore: number;
}

export class ScoreValidationError extends Error {
  constructor(message: string) {
    super(message);
    this.name = "ScoreValidationError";
  }
}

export const DEFAULT_REGULATION_INNINGS = 9;

export function validateScore(score: number): void {
  if (!Number.isInteger(score) || score < 0 || score > 9) {
    throw new ScoreValidationError("Scores must be whole numbers from 0 through 9.");
  }
}

export function validateInning(inningNumber: number): void {
  if (!Number.isInteger(inningNumber) || inningNumber <= 0) {
    throw new ScoreValidationError("Inning numbers must be positive.");
  }
}

export function targetForInning(inningNumber: number): number {
  validateInning(inningNumber);
  return inningNumber;
}

export function recordScore(
  scorecard: ParticipantScorecard,
  inningNumber: number,
  score: number
): ParticipantScorecard {
  validateScore(score);
  validateInning(inningNumber);
  return {
    ...scorecard,
    scoresByInning: {
      ...scorecard.scoresByInning,
      [inningNumber]: score
    }
  };
}

export function participantTotal(scorecard: ParticipantScorecard, throughInnings?: number): number {
  return Object.entries(scorecard.scoresByInning)
    .filter(([inning]) => throughInnings === undefined || Number(inning) <= throughInnings)
    .reduce((total, [, score]) => total + score, 0);
}

export function summarizeStandalone(
  participants: ParticipantScorecard[],
  regulationInnings = DEFAULT_REGULATION_INNINGS,
  extraInningsEnabled = true
): GameSummary {
  const maxEnteredInning = Math.max(
    regulationInnings,
    ...participants.flatMap((participant) => Object.keys(participant.scoresByInning).map(Number))
  );

  const participantSummaries = participants
    .map((participant) => {
      const enteredScores = Object.entries(participant.scoresByInning)
        .filter(([inning]) => Number(inning) <= maxEnteredInning)
        .map(([, score]) => score);
      const total = participantTotal(participant, maxEnteredInning);
      return {
        participantId: participant.participantId,
        displayName: participant.displayName,
        total,
        zeroCount: enteredScores.filter((score) => score === 0).length,
        nineCount: enteredScores.filter((score) => score === 9).length,
        performanceVersusAverage:
          participant.average === undefined ? undefined : total - participant.average
      };
    })
    .sort((lhs, rhs) => lhs.displayName.localeCompare(rhs.displayName));

  const highestTotal = Math.max(...participantSummaries.map((summary) => summary.total), 0);
  const leaders = participantSummaries
    .filter((summary) => summary.total === highestTotal)
    .map((summary) => summary.participantId);
  const orderedTotals = participantSummaries.map((summary) => summary.total).sort((a, b) => b - a);
  const tied = leaders.length > 1;

  return {
    participantSummaries,
    winnerIds: tied ? [] : leaders,
    margin: !tied && orderedTotals.length > 1 ? orderedTotals[0]! - orderedTotals[1]! : 0,
    inningsPlayed: maxEnteredInning,
    needsExtraInning: maxEnteredInning >= regulationInnings && tied && extraInningsEnabled
  };
}

export function calculateHandicap(
  teamAverageSums: Record<string, number>,
  handicapPercent: number,
  roundingRule: HandicapRoundingRule
): HandicapResult {
  const entries = Object.entries(teamAverageSums).sort(([leftId, left], [rightId, right]) => {
    if (left === right) return leftId.localeCompare(rightId);
    return left - right;
  });
  if (entries.length !== 2) return { handicap: 0 };
  const [lower, higher] = [entries[0]!, entries[1]!];
  if (lower[1] >= higher[1]) return { handicap: 0 };

  const raw = (higher[1] - lower[1]) * (handicapPercent / 100);
  const handicap =
    roundingRule === "nearest"
      ? Math.round(raw)
      : roundingRule === "floor"
        ? Math.floor(raw)
        : roundingRule === "ceiling"
          ? Math.ceil(raw)
          : raw;

  return {
    lowerAverageTeamId: lower[0],
    higherAverageTeamId: higher[0],
    handicap
  };
}

export function lockableGameIds(games: Game[], whenStartingGameNumber: number): string[] {
  return games
    .filter((game) => game.gameNumber < whenStartingGameNumber && game.status === "complete")
    .map((game) => game.gameId);
}

export function applyCorrection(
  score: InningScore,
  newValue: number,
  reason: string,
  editedByUserId: string,
  editedByRole: LeagueRole,
  affectedRecords: string[],
  now = new Date().toISOString()
): { correctedScore: InningScore; audit: CorrectionAuditRecord } {
  validateScore(newValue);
  if (reason.trim().length === 0) {
    throw new ScoreValidationError("A correction reason is required.");
  }

  const correctedScore = {
    ...score,
    score: newValue,
    updatedAt: now
  };

  return {
    correctedScore,
    audit: {
      correctionId: randomUUID(),
      leagueId: score.teamId ?? "standalone",
      matchId: score.gameId,
      gameId: score.gameId,
      inningScoreId: score.inningScoreId,
      editedByUserId,
      editedByRole,
      reason,
      previousValue: score.score,
      newValue,
      affectedRecords,
      status: "applied",
      createdAt: now,
      appliedAt: now
    }
  };
}

export function practiceStats(attempts: PracticeAttempt[]): PracticeStats {
  if (attempts.length === 0) {
    return { attempts: 0, average: 0, bestScore: 0, zeroCount: 0, nineCount: 0, recentTrend: 0 };
  }
  const scores = attempts.map((attempt) => attempt.score);
  const average = scores.reduce((sum, score) => sum + score, 0) / scores.length;
  const recent = scores.slice(-3);
  const previous = scores.slice(0, -Math.min(3, scores.length)).slice(-3);
  const avg = (values: number[]) =>
    values.length === 0 ? average : values.reduce((sum, score) => sum + score, 0) / values.length;

  return {
    attempts: scores.length,
    average,
    bestScore: Math.max(...scores),
    zeroCount: scores.filter((score) => score === 0).length,
    nineCount: scores.filter((score) => score === 9).length,
    recentTrend: avg(recent) - avg(previous)
  };
}

export function rankedStandings(standings: TeamStanding[]): TeamStanding[] {
  return [...standings].sort((lhs, rhs) => {
    if (lhs.leaguePoints !== rhs.leaguePoints) return rhs.leaguePoints - lhs.leaguePoints;
    if (lhs.gamesWon !== rhs.gamesWon) return rhs.gamesWon - lhs.gamesWon;
    if (lhs.pointDifferential !== rhs.pointDifferential) {
      return rhs.pointDifferential - lhs.pointDifferential;
    }
    if (lhs.totalAdjustedScore !== rhs.totalAdjustedScore) {
      return rhs.totalAdjustedScore - lhs.totalAdjustedScore;
    }
    if (lhs.totalRawScore !== rhs.totalRawScore) return rhs.totalRawScore - lhs.totalRawScore;
    return lhs.teamId.localeCompare(rhs.teamId);
  });
}

export function projectedFinalScore(
  currentScore: number,
  inningsPlayed: number,
  regulationInnings = DEFAULT_REGULATION_INNINGS
): number {
  return inningsPlayed <= 0 ? 0 : (currentScore / inningsPlayed) * regulationInnings;
}
