#!/usr/bin/env node
import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const repoRoot = path.resolve(__dirname, "../..");
const urlPattern = /https:\/\/[^\s")<]+/g;

export function extractUrlsFromText(text) {
  return [...new Set(text.match(urlPattern) ?? [])];
}

export function extractUrlsFromFile(relativePath) {
  const absolutePath = path.resolve(repoRoot, relativePath);
  return extractUrlsFromText(fs.readFileSync(absolutePath, "utf8"));
}

export async function validateUrlSyntax(urls) {
  return urls.map((url) => {
    const parsed = new URL(url);
    if (parsed.protocol !== "https:") throw new Error(`${url} must use https`);
    return url;
  });
}

export async function validateUrlReachability(urls) {
  if (process.env.CHECK_PUBLIC_URLS !== "1") return { checked: 0, skipped: urls.length };
  let checked = 0;
  for (const url of urls) {
    const response = await fetch(url, { method: "HEAD" });
    checked += 1;
    if (!response.ok && response.status >= 400) {
      throw new Error(`${url} returned ${response.status}`);
    }
  }
  return { checked, skipped: 0 };
}

export async function validateFiles(files) {
  const urls = [...new Set(files.flatMap(extractUrlsFromFile))];
  await validateUrlSyntax(urls);
  const reachability = await validateUrlReachability(urls);
  return { urls, ...reachability };
}

if (process.argv[1] && fileURLToPath(import.meta.url) === path.resolve(process.argv[1])) {
  const files = process.argv.slice(2);
  if (files.length === 0) {
    console.log("No files supplied. Pass markdown/json files to validate URLs.");
  } else {
    const result = await validateFiles(files);
    console.log(`URL validation passed: ${result.urls.length} valid URLs, ${result.checked} reached, ${result.skipped} reachability checks skipped.`);
  }
}
