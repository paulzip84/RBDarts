# Security Threat Model: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## Data Classification

Displayed data:

- Public app name: `RBDarts`.
- Public version/build label from Android release configuration.
- User-safe loading message.
- Local decorative artwork derived from `/Users/paulzip84/Downloads/RBDarts_Login`.

Touched but not displayed by this feature:

- Local startup route state: loading, unauthenticated, authenticated, session expired, offline, timeout.
- Coarse diagnostic attributes: `assetMode`, `routeResult`, `timingBucket`.

Sensitive data excluded from UI state, diagnostics, docs, and asset metadata:

- SSO access tokens, refresh tokens, ID tokens, and provider payloads.
- Provider account identifiers, emails, display names, and user ids.
- Session ids, raw session state, debug logs, stack traces, and crash payloads.
- Signing keys, Firebase production config, provider credentials, and reviewer secrets.

## Trust Boundaries

- The source image comes from the local Downloads folder and is treated as untrusted input until reviewed.
- The runtime image is a generated Android resource at `android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg`.
- Authentication and session decisions remain outside `LoadingScreen`; route decisions stay in `StartupRouteController` and app root state.
- Diagnostics are created through `UiDiagnostics`, which redacts disallowed attribute keys.

## Threats And Mitigations

| Threat | Mitigation |
| --- | --- |
| Raw source image is too large for startup | Runtime derivative is resized to 806 x 1800 JPEG and 347 KB. |
| Image includes unsafe metadata or hidden strings | Source and derivative are scanned before commit; only derivative is packaged for runtime. |
| Load screen leaks provider/session details | `LaunchPresentationState.safeLoadingMessage` filters sensitive terms and tests cover redaction. |
| Diagnostics include account or token data | `UiDiagnostics` redacts token, email, provider id, user id, session id, account, and debug log attributes. |
| Authenticated route is restored without auth | `StartupRouteController.routeFor` only restores known authenticated navigation routes for valid sessions. |
| Missing image creates blank startup | Fallback mode renders the existing local `rbdarts_loading_mark` vector and text/status. |
| Accessibility misses status | App name, version/build, loading status, screen label, and artwork description are exposed in Compose tests. |

## Review Status

- Security review status: Implemented with one manual screenshot evidence gap.
- Image metadata review: Runtime derivative created and documented in `asset-review.md`.
- Secret scan: Source and runtime derivative scans are recorded in `asset-review.md`.
- Automated redaction coverage: `LoadScreenDiagnosticsContractTest` and `LoadScreenPresentationStateTest`.
- Known exception: The runtime derivative is JPEG because local `sips` tooling does not write WebP. This still satisfies the optimized local Android asset requirement without network tooling or a new build dependency.
- Manual smoke note: Debug APK installation succeeded on `Medium_Phone_API_36.1`, but screenshot capture was blocked by emulator system ANRs followed by emulator disconnect.
