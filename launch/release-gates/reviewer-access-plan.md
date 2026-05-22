# Reviewer Access Plan

Status: Draft.

## Principle

Reviewer access must be revocable, isolated from production users, and supplied through platform review channels only.

## Required Setup

- Create Google and Facebook review identities managed by the release owner.
- Restrict review identities to launch validation data.
- Confirm review identities can create and complete a standalone game.
- Confirm no elevated admin privileges are granted.
- Provide platform-specific instructions in App Store Connect and Play Console.

## Repository Safety

- Do not commit reviewer credentials.
- Do not commit provider console exports.
- Do not commit private signing, provisioning, or keystore material.
