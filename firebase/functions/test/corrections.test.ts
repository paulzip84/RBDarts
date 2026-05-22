import assert from "node:assert/strict";
import test from "node:test";
import { applyApprovedCorrection, requestCorrection } from "../src/correctionOperations.ts";

test("correction workflow validates reason and creates audit", () => {
  assert.throws(() => requestCorrection("", 2, 7));

  const requested = requestCorrection("Wrong score", 2, 7);
  assert.equal(requested.status, "requested");

  const applied = applyApprovedCorrection(
    {
      inningScoreId: "score-1",
      gameId: "game-1",
      playerId: "player-1",
      teamId: "team-1",
      inningNumber: 7,
      target: 7,
      score: 2,
      enteredByUserId: "scorekeeper",
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString()
    },
    7,
    "Wrong score",
    "manager",
    "LeagueManager",
    ["game-1", "standings"]
  );
  assert.equal(applied.correctedScore.score, 7);
  assert.equal(applied.audit.previousValue, 2);
  assert.equal(applied.audit.status, "applied");
});
