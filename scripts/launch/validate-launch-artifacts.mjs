#!/usr/bin/env node
import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const repoRoot = path.resolve(__dirname, "../..");

const secretKeyPattern = /(password|secret|privateKey|token|appSpecificPassword|keystorePassword|reviewerPassword)/i;
const secretValuePattern = /(-----BEGIN|AKIA[0-9A-Z]{16}|ghp_[A-Za-z0-9_]+|AIza[0-9A-Za-z_-]{20,}|xox[baprs]-)/;

export function readJson(relativePath) {
  const absolutePath = path.resolve(repoRoot, relativePath);
  return JSON.parse(fs.readFileSync(absolutePath, "utf8"));
}

export function containsSecretLikeValue(value) {
  if (value === null || value === undefined) return false;
  if (typeof value === "string") return secretValuePattern.test(value);
  if (Array.isArray(value)) return value.some(containsSecretLikeValue);
  if (typeof value === "object") {
    return Object.entries(value).some(([key, nested]) => secretKeyPattern.test(key) || containsSecretLikeValue(nested));
  }
  return false;
}

function requireString(object, field, file) {
  if (typeof object[field] !== "string" || object[field].trim().length === 0) {
    throw new Error(`${file}: missing required string field ${field}`);
  }
}

function requireArray(object, field, file) {
  if (!Array.isArray(object[field]) || object[field].length === 0) {
    throw new Error(`${file}: missing required non-empty array field ${field}`);
  }
}

function validateLaunchCandidate(file) {
  const candidate = readJson(file);
  ["candidateId", "platform", "appVersion", "buildNumber", "bundleOrApplicationId", "releaseChannel", "configurationProfile", "readinessStatus", "gateResultFile"].forEach((field) => requireString(candidate, field, file));
  if (!["iOS", "Android"].includes(candidate.platform)) throw new Error(`${file}: invalid platform`);
  if (candidate.configurationProfile !== "production") throw new Error(`${file}: launch candidates must use production configuration`);
  if (containsSecretLikeValue(candidate)) throw new Error(`${file}: contains secret-like data`);
}

function validateStoreListing(file) {
  const listing = readJson(file);
  ["platform", "locale", "appName", "shortDescription", "fullDescription", "category", "supportUrl", "privacyPolicyUrl", "releaseNotes", "reviewNotesStatus", "screenshotChecklist"].forEach((field) => requireString(listing, field, file));
  requireArray(listing, "keywordsOrTags", file);
  for (const field of ["supportUrl", "privacyPolicyUrl"]) {
    try {
      const url = new URL(listing[field]);
      if (!["https:"].includes(url.protocol)) throw new Error("not https");
    } catch {
      throw new Error(`${file}: ${field} must be an https URL`);
    }
  }
  if (containsSecretLikeValue(listing)) throw new Error(`${file}: contains secret-like data`);
}

function validatePrivacyDisclosure(file) {
  const disclosure = readJson(file);
  ["platform", "reviewedAt", "status", "deletionPath"].forEach((field) => requireString(disclosure, field, file));
  requireArray(disclosure, "dataCategories", file);
  requireArray(disclosure, "thirdPartyServices", file);
  for (const category of disclosure.dataCategories) {
    ["name", "purpose"].forEach((field) => requireString(category, field, file));
    for (const field of ["collected", "linkedToUser", "shared"]) {
      if (typeof category[field] !== "boolean") throw new Error(`${file}: ${category.name} ${field} must be boolean`);
    }
  }
  if (containsSecretLikeValue(disclosure)) throw new Error(`${file}: contains secret-like data`);
}

function validateGateCatalog(file) {
  const catalog = readJson(file);
  requireArray(catalog, "gates", file);
  for (const gate of catalog.gates) {
    ["gateId", "name", "category", "severityIfFailed", "owner", "evidenceRequired", "blockingRule"].forEach((field) => requireString(gate, field, file));
  }
}

function validateBetaCycle(file) {
  const cycle = readJson(file);
  ["betaCycleId", "platform", "candidateId", "status"].forEach((field) => requireString(cycle, field, file));
  if (typeof cycle.testerCount !== "number") throw new Error(`${file}: testerCount must be a number`);
  requireArray(cycle, "deviceCoverage", file);
  requireArray(cycle, "journeysCovered", file);
}

function validateMonitoringPlan(file) {
  const plan = readJson(file);
  ["monitoringPlanId", "reviewCadence", "supportOwner", "engineeringOwner", "escalationPath", "hotfixCriteria", "rollbackCriteria"].forEach((field) => requireString(plan, field, file));
  requireArray(plan, "signals", file);
  if (!plan.thresholds || typeof plan.thresholds !== "object") throw new Error(`${file}: thresholds must be present`);
}

export function validateFile(file) {
  if (!fs.existsSync(path.resolve(repoRoot, file))) return { file, skipped: true };
  if (file.includes("launch-candidate")) validateLaunchCandidate(file);
  else if (file.includes("metadata/en-US.json")) validateStoreListing(file);
  else if (file.includes("privacy") && file.endsWith(".json")) validatePrivacyDisclosure(file);
  else if (file.includes("gate-catalog")) validateGateCatalog(file);
  else if (file.includes("beta-cycle")) validateBetaCycle(file);
  else if (file.includes("launch-monitoring-plan")) validateMonitoringPlan(file);
  else readJson(file);
  return { file, skipped: false };
}

export function defaultArtifactFiles() {
  return [
    "launch/release-gates/gate-catalog.json",
    "launch/release-gates/launch-candidate.template.json",
    "launch/release-gates/ios-launch-candidate.json",
    "launch/release-gates/android-launch-candidate.json",
    "launch/app-store/metadata/en-US.json",
    "launch/google-play/metadata/en-US.json",
    "launch/privacy/apple-app-privacy.json",
    "launch/privacy/google-data-safety.json",
    "launch/beta/ios-beta-cycle.json",
    "launch/beta/android-beta-cycle.json",
    "launch/runbooks/launch-monitoring-plan.json"
  ];
}

export function validateFiles(files = defaultArtifactFiles()) {
  return files.map(validateFile);
}

if (process.argv[1] && fileURLToPath(import.meta.url) === path.resolve(process.argv[1])) {
  const files = process.argv.slice(2);
  const results = validateFiles(files.length > 0 ? files : defaultArtifactFiles());
  const checked = results.filter((result) => !result.skipped).length;
  const skipped = results.filter((result) => result.skipped).length;
  console.log(`Launch artifact validation passed: ${checked} checked, ${skipped} skipped.`);
}
