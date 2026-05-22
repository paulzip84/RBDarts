import { projectedFinalScore, rankedStandings, summarizeStandalone, type ParticipantScorecard, type TeamStanding } from "./domain/scoringRules.ts";

export function createGameSummarySnapshot(participants: ParticipantScorecard[]) {
  return {
    summaryType: "game" as const,
    generatedAt: new Date().toISOString(),
    displayData: summarizeStandalone(participants)
  };
}

export function createStandingsSnapshot(standings: TeamStanding[]) {
  return {
    summaryType: "standings" as const,
    generatedAt: new Date().toISOString(),
    displayData: rankedStandings(standings)
  };
}

export function baselineProjection(currentScore: number, inningsPlayed: number, regulationInnings = 9) {
  return {
    label: "Estimate",
    projectedFinalScore: projectedFinalScore(currentScore, inningsPlayed, regulationInnings)
  };
}
