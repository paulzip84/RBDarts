# Android Release Candidate Checklist

Candidate record: `launch/release-gates/android-launch-candidate.json`

## Build

- Production application id confirmed.
- Version code is unique for Google Play.
- Release configuration validates as production.
- Crash reporting and performance monitoring use production Firebase.
- App name, launcher icon, and store-facing metadata contain no placeholder content.

## Smoke

- App launches on representative phone and tablet devices.
- Google SSO succeeds and cancellation is handled.
- Facebook SSO succeeds and cancellation is handled.
- Standalone scoring supports score entry, invalid score rejection, recovery, and final summary.
- Privacy, support, and account deletion links open without authentication.

## Approval

- Blocking issues: none recorded yet.
- Owner sign-off: pending beta evidence.
