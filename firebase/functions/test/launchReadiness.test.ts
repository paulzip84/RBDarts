import assert from "node:assert/strict";
import fs from "node:fs";
import test from "node:test";

const productionEnvUrl = new URL("../../.env.production.example", import.meta.url);

function readEnvTemplate(filePath: string | URL): Record<string, string> {
  const content = fs.readFileSync(filePath, "utf8");
  return Object.fromEntries(
    content
      .split(/\r?\n/)
      .filter((line) => line.includes("="))
      .map((line) => {
        const [key, ...valueParts] = line.split("=");
        return [key, valueParts.join("=")];
      })
  );
}

test("production Firebase launch template declares required availability controls", () => {
  const env = readEnvTemplate(productionEnvUrl);

  assert.equal(env.FIREBASE_PROJECT_ID, "rbdarts-production");
  assert.equal(env.APP_CHECK_ENFORCEMENT, "true");
  assert.ok(env.GOOGLE_WEB_CLIENT_ID);
  assert.ok(env.FACEBOOK_APP_ID);
});

test("production Firebase launch template does not include private credentials", () => {
  const content = fs.readFileSync(productionEnvUrl, "utf8");

  assert.doesNotMatch(content, /-----BEGIN/i);
  assert.doesNotMatch(content, /private_key/i);
  assert.doesNotMatch(content, /client_secret/i);
});
