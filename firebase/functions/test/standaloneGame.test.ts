import assert from "node:assert/strict";
import test from "node:test";
import { completeStandaloneGame } from "../src/standaloneGame.ts";

test("standalone score ownership and invalid score rejection", () => {
  assert.throws(() =>
    completeStandaloneGame([
      {
        participantId: "player-a",
        displayName: "Avery",
        scoresByInning: { 1: 10 }
      }
    ])
  );
});

test("standalone completion creates expected summary", () => {
  const summary = completeStandaloneGame([
    { participantId: "a", displayName: "A", scoresByInning: { 1: 9, 2: 9 } },
    { participantId: "b", displayName: "B", scoresByInning: { 1: 1, 2: 1 } }
  ]);
  assert.deepEqual(summary.winnerIds, ["a"]);
});
