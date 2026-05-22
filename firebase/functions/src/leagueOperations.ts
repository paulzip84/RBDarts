import type { CallableRequest } from "firebase-functions/v2/https";
import { requireAuth, requireNonEmpty } from "./trustedOperations.ts";

export interface CreateLeagueInput {
  leagueName: string;
  gamesPerMatch: number;
  playersPerTeamPerGame: number;
  handicapPercent: number;
}

export function createLeague(request: CallableRequest<CreateLeagueInput>) {
  const auth = requireAuth(request);
  const leagueName = requireNonEmpty(request.data.leagueName, "League name");
  if (request.data.gamesPerMatch < 1 || request.data.playersPerTeamPerGame < 1) {
    throw new Error("League game and lineup counts must be at least 1.");
  }
  if (request.data.handicapPercent < 0 || request.data.handicapPercent > 100) {
    throw new Error("Handicap percent must be between 0 and 100.");
  }

  return {
    leagueId: `league-${Date.now()}`,
    leagueName,
    createdByUserId: auth.uid,
    creatorRole: "LeagueManager" as const
  };
}
