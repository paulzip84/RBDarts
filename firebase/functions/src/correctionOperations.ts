import { applyCorrection } from "./domain/scoringRules.ts";
import type { InningScore, LeagueRole } from "./domain/types.ts";

export function requestCorrection(reason: string, previousValue: number, newValue: number) {
  if (reason.trim().length === 0) throw new Error("A correction reason is required.");
  if (!Number.isInteger(previousValue) || !Number.isInteger(newValue)) {
    throw new Error("Correction values must be integers.");
  }
  return {
    status: "requested" as const,
    previousValue,
    newValue,
    reason
  };
}

export function applyApprovedCorrection(
  score: InningScore,
  newValue: number,
  reason: string,
  editedByUserId: string,
  editedByRole: LeagueRole,
  affectedRecords: string[]
) {
  return applyCorrection(score, newValue, reason, editedByUserId, editedByRole, affectedRecords);
}
