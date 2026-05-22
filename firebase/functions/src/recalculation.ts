import { summarizeStandalone, rankedStandings, type ParticipantScorecard, type TeamStanding } from "./domain/scoringRules.ts";

export function recalculateGameSummary(participants: ParticipantScorecard[]) {
  return summarizeStandalone(participants);
}

export function recalculateStandings(standings: TeamStanding[]): TeamStanding[] {
  return rankedStandings(standings);
}

export function affectedRecordsForCorrection(gameId: string, playerId: string): string[] {
  return [gameId, playerId, "match-summary", "player-stats", "team-stats", "standings", "analytics"];
}
