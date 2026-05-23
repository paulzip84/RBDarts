# Design QA: Login Page Visual Redesign

Feature: `004-login-page-redesign`

## Visual Direction

- Dark premium login surface inspired by the attached reference.
- RBDarts mark is visible near the top.
- Primary headline is `Welcome back`.
- Provider buttons are stacked, readable, and touch-friendly.
- Support links are secondary and do not compete with SSO actions.

## SSO-Only UX Checks

- No email field.
- No password field.
- No password reset or `Forgot Password?` copy.
- Google and Facebook are the only provider actions.
- Help copy uses `Need help signing in?`.

## Accessibility Checks

- Provider buttons expose full labels.
- RBDarts mark has an accessibility description.
- Decorative background rings are not essential to understanding the screen.
- Text remains readable in dark and light theme.
- Compact width keeps provider actions visible.
- Large font manual QA still required on emulator/device.

## Screenshot Checklist

- [x] Default redesigned login captured at `specs/004-login-page-redesign/evidence/rbdarts-login-redesign.png`.
- [x] Provider loading state covered by `LoginProviderLoadingUiTest`.
- [x] Provider failure or cancellation state covered by `LoginProviderFailureUiTest` and `LoginProviderCancellationUiTest`.
- [x] Offline/session-expired state covered by `LoginOfflineUiTest` and `LoginSessionExpiredRouteTest`.
- [x] Dark theme and light fallback covered by `LoginDarkThemeTest` and `LoginLightThemeTest`.
- [x] Large font or compact phone state covered by `LoginLargeFontAdaptiveTest`.
