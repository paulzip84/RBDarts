# Security Threat Model: Android Material You UI

Feature: `003-android-material-you-ui`

## Security Priorities

- Authentication remains SSO-only for the MVP. The Android UI exposes Google and Facebook entry points through the existing `AuthProvider` abstraction and does not introduce first-party passwords.
- Protected app sections route unauthenticated users back to login through route metadata in `RBDartsRoutes.kt`.
- UI state stores provider names, display names, and route names only. Provider tokens, emails, passwords, raw identity payloads, signing keys, Firebase configs, and reviewer credentials are not represented in Compose state.
- Diagnostics use event names and coarse attributes only. Sensitive attribute keys such as token, secret, password, email, provider user id, and raw score payloads are filtered before event creation.

## Data Classification

- Public: App name, version label, Material You route names, and general support/privacy URLs.
- User-visible app data: Player display names, nicknames, season names, seed averages, handicap values, and scoring totals.
- Sensitive: SSO provider tokens, provider account identifiers, emails, Firebase config, signing keys, crash reports with personal data, and store credentials.
- Restricted: Production release configuration, provider console exports, keystores, provisioning files, and reviewer credentials.

## Threats And Controls

- Unauthorized route access: Protected route metadata redirects signed-out users to login.
- Provider failure/cancel leakage: Failure states show safe messages without provider payloads.
- Asset injection or remote fetch: The loading image is committed as a local raw source and Android drawable fallback; the loading screen does not fetch remote media.
- Diagnostic privacy regression: `UiDiagnostics` filters sensitive keys and unit tests assert redaction.
- Shoulder-surfing risk: UI uses sample player names in local seed states and launch docs instruct screenshot capture to avoid real account details.

## Residual Risks

- Local SSO providers are demo implementations until production Google/Facebook provider configuration is installed.
- Compose instrumentation tests are source-compiled locally; device execution still needs emulator evidence.
- The app still needs production Firebase config and provider console validation before store submission.

## Secret Scan

- Status: Passed on 2026-05-22.
- Command: `rg -n -i "(api[_-]?key|client_secret|secret|password|token|private key|BEGIN RSA|BEGIN PRIVATE|AIza|keystore|providerUserId)" README.md android/app/src/main specs/003-android-material-you-ui launch/google-play/screenshots/screenshot-checklist.md`
- Result: Matches are policy text, redaction key lists, or non-secret code symbols only. No committed credential values were found.
- Asset command: `rg -n -i "(http|https|script|token|secret|password|api[_-]?key|client_secret|BEGIN PRIVATE|AIza)" android/app/src/main/res/raw/rbdarts_loading_source.svg android/app/src/main/res/drawable/rbdarts_loading_mark.xml`
- Asset result: Only XML namespace/DTD URLs were found. No scripts, credentials, or remote runtime references were found.
