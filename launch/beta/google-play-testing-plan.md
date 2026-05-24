# Google Play Testing Plan

Status: Draft.

## Scope

- Candidate: `android-0.1.0-1-draft`
- Track: Internal testing first, then closed testing if confidence requires broader coverage.
- Minimum testers: 5 internal testers before closed testing.
- Device coverage: current Android phone, lower-memory Android phone, tablet if supported.

## Journeys

- Google SSO sign-in and sign-out.
- Facebook SSO sign-in and sign-out.
- Standalone game creation, scoring, correction, completion, and summary.
- League setup and standings review.
- Practice mode scoring.
- App interruption, restart, network loss, and recovery.
- Privacy/support/account deletion link access.

## Exit Criteria

- No critical crash or data loss.
- No sign-in blocker.
- No unrecoverable active scoring state.
- Store package screenshots match the beta candidate.
