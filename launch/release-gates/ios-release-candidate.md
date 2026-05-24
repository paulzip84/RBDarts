# iOS Release Candidate Checklist

Candidate record: `launch/release-gates/ios-launch-candidate.json`

## Build

- Production bundle id confirmed.
- Build number is unique for App Store Connect.
- Release configuration validates as production.
- Crash reporting and performance monitoring use production Firebase.
- App icon, display name, and launch metadata contain no placeholder content.

## Smoke

- App launches on supported iPhone and iPad classes.
- Google SSO succeeds and cancellation is handled.
- Facebook SSO succeeds and cancellation is handled.
- Standalone scoring supports score entry, invalid score rejection, recovery, and final summary.
- Privacy, support, and account deletion links open without authentication.

## Approval

- Blocking issues: none recorded yet.
- Owner sign-off: pending beta evidence.
