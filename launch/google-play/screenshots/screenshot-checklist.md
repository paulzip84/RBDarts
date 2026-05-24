# Google Play Screenshot Checklist

Status: Draft, pending capture from signed Android release candidate.

## Required Scenes

- Loading screen with RBDarts image, app name, and version/build visible.
- Redesigned Login screen with RBDarts mark, `Welcome back`, and Google/Facebook SSO actions.
- Authenticated Home dashboard in the dark shell with the hamburger button visible.
- Authenticated navigation drawer open with Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, and Sign Out visible.
- Dark Game Type setup summary.
- Player creation form with sample player data.
- Season creation form with sample season data.
- Player-level handicap management and scoring impact preview.
- Robust Scoring screen with representative players and running totals.
- Scorecard review or completion state after a completed match.
- League setup or standings workflow.
- Practice mode scoring workflow.
- Stats summary or projection view.
- Privacy/support/account deletion screen.

## Capture Rules

- Use the signed candidate that matches `android-launch-candidate.json`.
- Do not show real player names, emails, or account details.
- Use realistic sample names only.
- Capture phone and tablet sizes required by the Play Console.
- Confirm screenshots match the final metadata claims.

## Evidence

- Screenshot file names and device sizes will be recorded here before submission.
- 005 load-screen smoke evidence is tracked in `specs/005-load-screen-redesign/evidence/load-screen-smoke.md`.
- 006 authenticated shell smoke evidence is tracked in `specs/006-reface-rest-android/evidence/authenticated-shell-smoke.md`.
