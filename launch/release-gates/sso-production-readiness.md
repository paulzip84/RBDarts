# SSO Production Readiness

Status: Draft.

## Providers

- Google Sign-In
- Facebook Login

## Required Checks

- Production client IDs configured for iOS and Android bundle/application identities.
- Redirect schemes and package signatures match production builds.
- Provider console app status allows store reviewers to sign in.
- Cancellation and provider error paths do not expose raw provider responses.
- Reviewer access identities are revocable and isolated from production users.

## Result

No repository blocker. Final pass requires provider console evidence before beta and store submission.
