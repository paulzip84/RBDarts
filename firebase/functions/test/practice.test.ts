import assert from "node:assert/strict";
import test from "node:test";
import { calculatePracticeStats, savePracticeAttempt } from "../src/practiceOperations.ts";

test("practice attempts are valid and isolated from official stats", () => {
  const attempts = [0, 4, 5, 9, 5].map((score, index) =>
    savePracticeAttempt({
      practiceAttemptId: `p${index}`,
      playerId: "player-a",
      userId: "user-a",
      target: 5,
      score,
      attemptDate: new Date().toISOString(),
      createdAt: new Date().toISOString()
    })
  );

  const stats = calculatePracticeStats(attempts);
  assert.equal(stats.attempts, 5);
  assert.equal(stats.average, 4.6);
  assert.equal(stats.bestScore, 9);
});
