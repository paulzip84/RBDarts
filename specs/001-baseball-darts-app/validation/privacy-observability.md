# Privacy And Observability Review

## Current Coverage

- iOS and Android diagnostics wrappers redact token, secret, password, email,
  correction reason, and provider user id attributes.
- Diagnostics events use operational names such as invalid score, recovery,
  lock transition, save failure, and stale offline data.
- Shared fixtures contain synthetic names and ids only.

## Required Pre-Release Checks

- Review Crashlytics keys before enabling production collection.
- Review Firebase Performance traces for personal data in custom attributes.
- Confirm correction reasons never appear in logs or analytics events.
- Confirm export operations record audit metadata and visible export scope.
