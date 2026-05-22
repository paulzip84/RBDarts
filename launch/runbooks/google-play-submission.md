# Google Play Submission Runbook

Status: Draft.

## Preconditions

- Signed Android App Bundle uploaded to Play Console.
- `android-launch-candidate.json` matches the uploaded build.
- Data Safety answers reviewed against `launch/privacy/google-data-safety.json`.
- Screenshots captured from the release candidate.
- App access instructions entered in Play Console without committed reviewer credentials.
- Public privacy, support, and account deletion URLs are reachable.

## Submission Steps

1. Confirm Play Console app id is `com.rbdarts`.
2. Select the internal or closed testing build for review.
3. Attach screenshots and metadata for `en-US`.
4. Complete App Content, Data Safety, content rating, and target audience sections.
5. Enter app access instructions from `launch/google-play/app-content/app-access.md`.
6. Submit changes for review.

## After Submission

- Record submission time in `launch/release-gates/decision-log.md`.
- Monitor policy status at least once per business day.
- Use `launch/google-play/app-content/review-response-template.md` for reviewer questions.
