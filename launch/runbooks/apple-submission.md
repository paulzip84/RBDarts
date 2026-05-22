# Apple Submission Runbook

Status: Draft.

## Preconditions

- Signed iOS archive uploaded to App Store Connect.
- `ios-launch-candidate.json` matches the uploaded build.
- App privacy answers reviewed against `launch/privacy/apple-app-privacy.json`.
- Screenshots captured from the release candidate.
- Review notes entered in App Store Connect without committed reviewer credentials.
- Public privacy, support, and account deletion URLs are reachable.

## Submission Steps

1. Confirm App Store Connect app record uses bundle id `com.rbdarts.app`.
2. Select the uploaded build and verify version `0.1.0`.
3. Attach screenshots and metadata for `en-US`.
4. Complete age rating and compliance answers.
5. Complete App Privacy answers.
6. Enter reviewer instructions from `launch/app-store/review-notes/review-notes.md`.
7. Submit for review.

## After Submission

- Record submission time in `launch/release-gates/decision-log.md`.
- Monitor review status at least once per business day.
- Use `launch/app-store/review-notes/review-response-template.md` for reviewer questions.
