# Security Threat Model: Login Page Visual Redesign

Feature: `004-login-page-redesign`

## Data Classification

- Public: RBDarts brand name, app mark, provider names, support link labels, privacy link labels, account deletion label.
- User-visible, low sensitivity: Coarse login status messages such as cancelled, failed, offline, or session expired.
- Sensitive: SSO tokens, provider account identifiers, provider profile payloads, emails, display names, session state, raw provider errors, user ids, and provider configuration values.
- Restricted: Production provider configuration, Firebase config, signing keys, keystores, reviewer credentials, and release signing material.

## Trust Boundaries

- Android Compose UI boundary: shows display-safe state only.
- Provider boundary: Google/Facebook SDKs or provider flows own identity proof and credentials.
- App routing boundary: unauthenticated users must remain on login until a valid session exists.
- Diagnostics boundary: only coarse event names and safe reason codes may leave the UI layer.

## Threats And Mitigations

- First-party credential collection: Mitigated by forbidding email, password, and password-reset UI in spec, state model, and tests.
- Provider payload leakage: Mitigated by redacting token, email, provider user id, raw provider response, display name, and user id keys in diagnostics.
- Double-submit sign-in: Mitigated by disabling all provider actions while one provider is loading.
- Misleading recovery copy: Mitigated by using `Need help signing in?` and support/account links instead of password-reset language.
- Unauthenticated route bypass: Mitigated by preserving existing protected route contracts and login redirect tests.
- Asset or remote fetch risk: Mitigated by using local drawable assets only for provider marks and RBDarts logo.
- Accessibility failure causing auth lockout: Mitigated through UI tests for labels, large/compact layout, and dark/light rendering.

## Secret Scan

- Status: Passed on 2026-05-22.
- Command: `rg -n -i "(api[_-]?key|client_secret|secret|password|token|private key|BEGIN RSA|BEGIN PRIVATE|AIza|keystore|providerUserId|rawProviderResponse)" README.md android/app/src/main specs/004-login-page-redesign launch/google-play/screenshots/screenshot-checklist.md`
- Result: Matches are policy text, redaction key lists, or non-secret code symbols only. No credential values were found.
- Asset command: `rg -n -i "(http|https|script|token|secret|password|api[_-]?key|client_secret|BEGIN PRIVATE|AIza)" android/app/src/main/res/drawable/ic_google_mark.xml android/app/src/main/res/drawable/ic_facebook_mark.xml android/app/src/main/res/drawable/rbdarts_loading_mark.xml`
- Asset result: Only Android XML namespace URLs were found. No remote runtime fetches, scripts, or credential material were found.
- UI credential copy check: `rg -n -i "password|email|forgot" android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt android/app/src/main/java/com/rbdarts/core/designsystem/LoginComponents.kt` returned no matches.
