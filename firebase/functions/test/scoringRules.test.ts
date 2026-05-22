import assert from "node:assert/strict";
import test from "node:test";
import {
  calculateHandicap,
  recordScore,
  summarizeStandalone,
  validateScore
} from "../src/domain/scoringRules.ts";

test("standalone fixture totals and winner", () => {
  const summary = summarizeStandalone([
    {
      participantId: "player-a",
      displayName: "Avery",
      scoresByInning: { 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9 }
    },
    {
      participantId: "player-b",
      displayName: "Blake",
      scoresByInning: { 1: 0, 2: 3, 3: 3, 4: 4, 5: 4, 6: 5, 7: 6, 8: 7, 9: 8 }
    }
  ]);

  assert.equal(summary.participantSummaries.find((item) => item.participantId === "player-a")?.total, 45);
  assert.equal(summary.participantSummaries.find((item) => item.participantId === "player-b")?.total, 40);
  assert.deepEqual(summary.winnerIds, ["player-a"]);
  assert.equal(summary.margin, 5);
});

test("invalid scores are rejected", () => {
  assert.throws(() => validateScore(-1));
  assert.throws(() => validateScore(10));
  assert.throws(() => recordScore({ participantId: "a", displayName: "A", scoresByInning: {} }, 1, 10));
});

test("handicap rounding matches fixture", () => {
  const averages = { higher: 72, lower: 63.4 };
  assert.equal(calculateHandicap(averages, 80, "nearest").handicap, 7);
  assert.equal(calculateHandicap(averages, 80, "floor").handicap, 6);
  assert.equal(calculateHandicap(averages, 80, "ceiling").handicap, 7);
  assert.ok(Math.abs(calculateHandicap(averages, 80, "decimal").handicap - 6.88) < 0.001);
});
