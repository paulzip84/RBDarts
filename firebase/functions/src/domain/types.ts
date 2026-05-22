export type AccountStatus = "active" | "disabled" | "deleted";
export type ActiveStatus = "active" | "inactive";
export type LeagueRole =
  | "LeagueManager"
  | "TeamManager"
  | "Scorekeeper"
  | "Player"
  | "Viewer"
  | "Admin";
export type GameStatus =
  | "setup"
  | "scoring"
  | "complete"
  | "locked"
  | "finalized"
  | "corrected"
  | "voided";
export type MatchStatus = "scheduled" | "inProgress" | "finalized" | "corrected" | "voided";
export type HandicapRoundingRule = "nearest" | "floor" | "ceiling" | "decimal";
export type CorrectionStatus = "requested" | "approved" | "rejected" | "applied";

export interface UserAccount {
  userId: string;
  displayName: string;
  email?: string;
  linkedProviderIds: string[];
  accountStatus: AccountStatus;
  createdAt: string;
  updatedAt: string;
}

export interface RoleAssignment {
  roleAssignmentId: string;
  userId: string;
  leagueId: string;
  teamId?: string;
  role: LeagueRole;
  permissions: string[];
  createdAt: string;
  updatedAt: string;
}

export interface Player {
  playerId: string;
  userId?: string;
  firstName?: string;
  lastName?: string;
  displayName: string;
  activeStatus: ActiveStatus;
  createdAt: string;
  updatedAt: string;
}

export interface League {
  leagueId: string;
  leagueName: string;
  gamesPerMatch: number;
  playersPerTeamPerGame: number;
  handicapPercent: number;
  handicapRoundingRule: HandicapRoundingRule;
  pointsPerGameWin: number;
  matchBonusPointValue: number;
  averageUpdateRule: "afterMatch";
  extraInningsRule: "enabled" | "disabled" | "leagueTieRule";
  tieBonusRule: "noBonus" | "splitBonus" | "tiebreakerGame" | "rawScoreTiebreak";
  createdAt: string;
  updatedAt: string;
}

export interface Team {
  teamId: string;
  leagueId: string;
  teamName: string;
  captainPlayerId?: string;
  activeStatus: ActiveStatus;
  createdAt: string;
  updatedAt: string;
}

export interface Game {
  gameId: string;
  matchId?: string;
  standaloneGameId?: string;
  gameNumber: number;
  status: GameStatus;
  awayTeamRawScore: number;
  homeTeamRawScore: number;
  awayTeamHandicap: number;
  homeTeamHandicap: number;
  winningTeamId?: string;
  inningsPlayed: number;
  lockedAt?: string;
}

export interface InningScore {
  inningScoreId: string;
  gameId: string;
  playerId: string;
  teamId?: string;
  inningNumber: number;
  target: number;
  score: number;
  enteredByUserId: string;
  createdAt: string;
  updatedAt: string;
}

export interface PracticeAttempt {
  practiceAttemptId: string;
  playerId: string;
  userId: string;
  target: number;
  score: number;
  attemptDate: string;
  createdAt: string;
}

export interface CorrectionAuditRecord {
  correctionId: string;
  leagueId: string;
  matchId: string;
  gameId: string;
  inningScoreId: string;
  editedByUserId: string;
  editedByRole: LeagueRole;
  reason: string;
  previousValue: number;
  newValue: number;
  affectedRecords: string[];
  status: CorrectionStatus;
  createdAt: string;
  appliedAt?: string;
}
