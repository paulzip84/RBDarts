# Evidence Template: US1 Loading And Authentication

## Automated Coverage

- `LaunchPresentationStateTest`
- `AuthUiStateTest`
- `StartupRouteControllerTest`
- `LoadingScreenTest`
- `LoginScreenTest`
- `LoginFailureStateTest`
- `LoadingAccessibilityTest`

## Manual Smoke

- Cold launch shows local loading image, `RBDarts`, and version/build.
- Unauthenticated startup routes to login.
- Google and Facebook SSO actions are visible.
- Failure or cancellation messaging stays on login and avoids provider payload details.
