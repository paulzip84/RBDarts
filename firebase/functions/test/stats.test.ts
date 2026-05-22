import assert from "node:assert/strict";
import test from "node:test";
import { baselineProjection, createStandingsSnapshot } from "../src/statsOperations.ts";

test("stats projections and standings ranking", () => {
  assert.equal(baselineProjection(20, 4).projectedFinalScore, 45);

  const snapshot = createStandingsSnapshot([
    { teamId: "b", leaguePoints: 12, gamesWon: 6, pointDifferential: 12, totalAdjustedScore: 320, totalRawScore: 318 },
    { teamId: "a", leaguePoints: 12, gamesWon: 6, pointDifferential: 24, totalAdjustedScore: 310, totalRawScore: 300 }
  ]);
  assert.deepEqual(snapshot.displayData.map((team) => team.teamId), ["a", "b"]);
});
