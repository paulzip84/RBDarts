import assert from "node:assert/strict";
import test from "node:test";
import { calculateGameHandicap } from "../src/matchOperations.ts";
import { startGameAndLockPrior } from "../src/matchFinalization.ts";

test("league handicap and lock transitions", () => {
  const handicap = calculateGameHandicap({ home: 72, away: 63.4 }, 80, "nearest");
  assert.equal(handicap.lowerAverageTeamId, "away");
  assert.equal(handicap.handicap, 7);

  const locked = startGameAndLockPrior(
    [
      {
        gameId: "game-1",
        gameNumber: 1,
        status: "complete",
        awayTeamRawScore: 0,
        homeTeamRawScore: 0,
        awayTeamHandicap: 0,
        homeTeamHandicap: 0,
        inningsPlayed: 9
      },
      {
        gameId: "game-2",
        gameNumber: 2,
        status: "setup",
        awayTeamRawScore: 0,
        homeTeamRawScore: 0,
        awayTeamHandicap: 0,
        homeTeamHandicap: 0,
        inningsPlayed: 0
      }
    ],
    2
  );
  assert.deepEqual(locked, ["game-1"]);
});
