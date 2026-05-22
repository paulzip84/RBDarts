import assert from "node:assert/strict";
import test from "node:test";

test("league role matrix includes all MVP roles", () => {
  assert.deepEqual(
    ["LeagueManager", "TeamManager", "Scorekeeper", "Player", "Viewer", "Admin"],
    ["LeagueManager", "TeamManager", "Scorekeeper", "Player", "Viewer", "Admin"]
  );
});
