import { lockableGameIds, rankedStandings, type TeamStanding } from "./domain/scoringRules.ts";
import type { Game } from "./domain/types.ts";

export function startGameAndLockPrior(games: Game[], gameNumber: number): string[] {
  return lockableGameIds(games, gameNumber);
}

export function finalizeStandings(standings: TeamStanding[]): TeamStanding[] {
  return rankedStandings(standings);
}
