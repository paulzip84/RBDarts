# Evidence: Login Page Visual Redesign Smoke

Feature: `004-login-page-redesign`

## Automated Coverage

- `LoginNoPasswordContractTest`
- `LoginPresentationStateTest`
- `SsoProviderActionTest`
- `LoginDiagnosticsContractTest`
- `LoginNavigationContractTest`
- `LoginPremiumScreenTest`
- `LoginNoPasswordUiTest`
- `LoginDarkThemeTest`
- `LoginLightThemeTest`
- `LoginLargeFontAdaptiveTest`
- `LoginAccessibilityTest`
- `LoginProviderLoadingUiTest`
- `LoginProviderCancellationUiTest`
- `LoginProviderFailureUiTest`
- `LoginProviderDoubleTapTest`
- `LoginSuccessRouteTest`
- `LoginHelpLinkUiTest`
- `LoginSupportLinksUiTest`
- `LoginOfflineUiTest`
- `LoginSessionExpiredRouteTest`

## Manual Smoke

- Status: Passed on 2026-05-22 with `emulator-5554`.
- Installed debug APK from `android/app/build/outputs/apk/debug/app-debug.apk`.
- Launched the installed app with `adb shell monkey -p com.rbdarts 1`.
- Confirmed foreground state resolved to `com.rbdarts/com.rbdarts.app.MainActivity`.
- Captured redesigned login screenshot at `specs/004-login-page-redesign/evidence/rbdarts-login-redesign.png`.

## Screenshot Evidence

- Default redesigned login screenshot: `specs/004-login-page-redesign/evidence/rbdarts-login-redesign.png`.
- Provider/error states are covered by Compose UI test source compilation and should be captured during connected UI test execution or manual provider-console QA.
