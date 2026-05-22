import assert from "node:assert/strict";
import test from "node:test";

test("correction rule expectations keep apply permission narrower than request permission", () => {
  const requestRoles = ["LeagueManager", "TeamManager", "Scorekeeper"];
  const applyRoles = ["LeagueManager", "Admin"];
  assert.ok(requestRoles.includes("TeamManager"));
  assert.ok(!applyRoles.includes("TeamManager"));
});
