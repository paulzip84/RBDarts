# Contract: Startup Routing

Feature: `005-load-screen-redesign`

## Entry

- App starts in `Loading` route.
- Load screen renders immediately with local assets and safe display state.
- Startup/session validation begins outside the load screen composable.

## Route Outcomes

| Startup result | Destination | Load-screen behavior |
| --- | --- | --- |
| No valid session | Login | Transition away from loading once local state is resolved |
| Valid session | Home or safe preserved route | Transition away from loading once authenticated state is confirmed |
| Session expired | Login | Do not show raw session detail on load screen |
| Offline with no session | Login | Route to login/recovery state; do not block indefinitely |
| Timeout | Login or safe retry state | Keep load screen safe until fallback route decision is made |

## Preserved Route

A preserved authenticated route may be used only when:

- It is a known route.
- It does not encode sensitive values.
- The user is authenticated.
- The route remains allowed after session validation.

Otherwise route to Home or Login.

## Diagnostics

Allowed load-screen diagnostics:

- `android_load_screen_viewed`
- `android_load_artwork_mode`
- `android_load_route_completed`
- `android_load_route_delayed`

Allowed attributes:

- `assetMode`: `image`, `fallback`, or `missing`.
- `routeResult`: `login`, `home`, `session_expired`, `offline`, or `timeout`.
- `timingBucket`: `lt_1s`, `1_to_2s`, `gt_2s`.

Disallowed attributes:

- `token`
- `email`
- `providerUserId`
- `rawProviderResponse`
- `userId`
- `displayName`
- `sessionId`
- `debugLog`

## Back And Lifecycle

- Back press on load screen during cold launch may exit the app if no destination is ready.
- Rotation or background/resume must not restart routing in a way that causes duplicate navigation.
- If Android recreates the activity, load state should re-resolve safely and route to the correct destination.

## Implementation Notes

- `StartupRouteController.initialRoute()` returns `RBDartsRoute.Loading`.
- `StartupRouteController.routeAfterLoading(hasSession = false)` routes to `Login`.
- `StartupRouteController.routeAfterLoading(hasSession = true)` routes to `Home`.
- `StartupRouteController.routeFor(ValidSession, preservedRouteName)` only restores known authenticated top-level routes.
- Session-expired, offline-without-session, and timeout outcomes route to `Login` without exposing raw session/provider details on the load screen.
