import assert from "node:assert/strict";
import fs from "node:fs";
import os from "node:os";
import path from "node:path";
import test from "node:test";
import { containsSecretLikeValue, validateFiles } from "./validate-launch-artifacts.mjs";
import { extractUrlsFromText, validateUrlSyntax } from "./check-public-urls.mjs";

test("launch artifact validator accepts committed launch artifacts", () => {
  const results = validateFiles();
  assert.ok(results.length >= 1);
  assert.equal(results.some((result) => result.skipped === false), true);
});

test("launch artifact validator rejects secret-like values", () => {
  assert.equal(containsSecretLikeValue({ apiToken: "ghp_1234567890abcdefghijklmnopqrstuvwxyz" }), true);
  assert.equal(containsSecretLikeValue({ supportUrl: "https://rbdarts.app/support" }), false);
});

test("store listing package requires https support and privacy urls", async () => {
  const urls = extractUrlsFromText("Support: https://rbdarts.app/support Privacy: https://rbdarts.app/privacy");
  assert.deepEqual(urls, ["https://rbdarts.app/support", "https://rbdarts.app/privacy"]);
  await validateUrlSyntax(urls);
});

test("schema files are valid JSON", () => {
  const schemaDir = path.resolve("scripts/launch/schemas");
  const schemas = fs.readdirSync(schemaDir).filter((file) => file.endsWith(".json"));
  assert.ok(schemas.length >= 6);
  for (const schema of schemas) {
    const parsed = JSON.parse(fs.readFileSync(path.join(schemaDir, schema), "utf8"));
    assert.equal(parsed.type, "object");
  }
});

test("temporary launch candidate fixture can be parsed without storing secrets", () => {
  const tempDir = fs.mkdtempSync(path.join(os.tmpdir(), "rbdarts-launch-"));
  const candidate = {
    candidateId: "fixture",
    platform: "iOS",
    appVersion: "0.1.0",
    buildNumber: "1",
    bundleOrApplicationId: "com.rbdarts.app",
    releaseChannel: "beta",
    configurationProfile: "production",
    readinessStatus: "betaReady",
    gateResultFile: "launch/release-gates/build-validation.md"
  };
  const file = path.join(tempDir, "candidate.json");
  fs.writeFileSync(file, JSON.stringify(candidate));
  assert.equal(JSON.parse(fs.readFileSync(file, "utf8")).configurationProfile, "production");
});

test("committed launch candidate records satisfy production launch contract", () => {
  const results = validateFiles([
    "launch/release-gates/ios-launch-candidate.json",
    "launch/release-gates/android-launch-candidate.json"
  ]);

  assert.equal(results.every((result) => result.skipped === false), true);
});

test("committed store listing packages satisfy metadata contract", () => {
  const results = validateFiles([
    "launch/app-store/metadata/en-US.json",
    "launch/google-play/metadata/en-US.json"
  ]);

  assert.equal(results.every((result) => result.skipped === false), true);
});

test("committed privacy disclosures satisfy privacy contract", () => {
  const results = validateFiles([
    "launch/privacy/apple-app-privacy.json",
    "launch/privacy/google-data-safety.json"
  ]);

  assert.equal(results.every((result) => result.skipped === false), true);
});

test("committed beta cycles and release gates satisfy gate contracts", () => {
  const results = validateFiles([
    "launch/beta/ios-beta-cycle.json",
    "launch/beta/android-beta-cycle.json",
    "launch/release-gates/gate-catalog.json"
  ]);

  assert.equal(results.every((result) => result.skipped === false), true);
});

test("committed launch monitoring plan satisfies monitoring contract", () => {
  const results = validateFiles(["launch/runbooks/launch-monitoring-plan.json"]);

  assert.equal(results.every((result) => result.skipped === false), true);
});

test("store review issue tracker and launch readiness report contain actionable fields", () => {
  const issueTracker = fs.readFileSync("launch/runbooks/store-review-issues.md", "utf8");
  const report = fs.readFileSync("launch/release-gates/launch-readiness-report.md", "utf8");

  assert.match(issueTracker, /Issue ID/);
  assert.match(issueTracker, /Response Due/);
  assert.match(report, /Gate Summary/);
  assert.match(report, /Decision/);
});

test("runbook link validation extracts support and policy links", async () => {
  const runbook = fs.readFileSync("launch/runbooks/store-policy-refresh.md", "utf8");
  const urls = extractUrlsFromText(runbook);

  assert.ok(urls.some((url) => url.includes("developer.apple.com")));
  assert.ok(urls.some((url) => url.includes("support.google.com")));
  await validateUrlSyntax(urls);
});
