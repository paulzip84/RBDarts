import { practiceStats } from "./domain/scoringRules.ts";
import type { PracticeAttempt } from "./domain/types.ts";

export function savePracticeAttempt(attempt: PracticeAttempt): PracticeAttempt {
  if (attempt.score < 0 || attempt.score > 9 || !Number.isInteger(attempt.score)) {
    throw new Error("Practice scores must be whole numbers from 0 through 9.");
  }
  if (attempt.target <= 0 || !Number.isInteger(attempt.target)) {
    throw new Error("Practice target must be a positive inning number.");
  }
  return attempt;
}

export function calculatePracticeStats(attempts: PracticeAttempt[]) {
  return practiceStats(attempts);
}
