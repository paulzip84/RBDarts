# Data Model: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## LoadScreenPresentation

Represents the display-safe state needed to render the load screen.

Fields:

- `appName`: Expected `RBDarts`.
- `versionName`: App version.
- `buildNumber`: Build number.
- `versionLabel`: Display-safe version/build label.
- `loadingMessage`: User-visible startup message.
- `artwork`: `LoadArtworkAsset`.
- `fallback`: `FallbackBrandState`.
- `routeStatus`: `StartupRouteStatus`.
- `accessibilityLabel`: Screen-level label.

Rules:

- Must not include provider tokens, provider payloads, emails, user ids, debug logs, or raw session details.
- Must always have a safe text fallback if version/build cannot be read.
- Must expose app name, version/build, and loading status to assistive technology.

## LoadArtworkAsset

Represents the source and runtime artwork metadata.

Fields:

- `sourcePath`: Source image path, currently `/Users/paulzip84/Downloads/RBDarts_Login`.
- `runtimeResourceName`: Optimized Android resource name.
- `sourceDimensions`: Source dimensions, currently `1280 x 2856`.
- `runtimeDimensions`: Optimized derivative dimensions.
- `format`: PNG, WebP, or other Android-supported local format.
- `focalRegion`: Describes the important area to preserve during crop.
- `metadataReviewStatus`: `Pending`, `Passed`, or `Failed`.
- `assetSafetyNotes`: Review notes.

Rules:

- Runtime asset must be local and packaged with the app.
- Runtime asset must not require network fetch.
- Source and runtime derivatives must be reviewed for secrets, metadata, scripts, and remote references.
- Runtime asset should avoid avoidable APK bloat and startup memory pressure.

## StartupRouteState

Represents route state while the load screen is visible.

Fields:

- `status`: `Loading`, `Unauthenticated`, `Authenticated`, `SessionExpired`, `Offline`, or `TimedOut`.
- `targetRoute`: Login, Home, or safe preserved route.
- `safeMessage`: Optional user-safe status text.
- `timingBucket`: Coarse startup timing bucket.

Rules:

- Must not include raw provider failure, token lifecycle state, or account identifiers.
- Authenticated routes require a valid authenticated state.
- On failure or expiration, route to login with safe recovery messaging.

## LoadDiagnosticEvent

Represents privacy-safe observability.

Fields:

- `name`: Event name such as `android_load_screen_viewed` or `android_load_route_completed`.
- `routeResult`: `login`, `home`, `session_expired`, `offline`, or `timeout`.
- `timingBucket`: Coarse timing bucket such as `lt_1s`, `1_to_2s`, or `gt_2s`.
- `assetMode`: `image`, `fallback`, or `missing`.

Rules:

- Must not include tokens, emails, raw provider responses, user ids, account details, or exact personal activity.
- Must be covered by redaction tests.

## FallbackBrandState

Represents the backup visual state if image artwork fails.

Fields:

- `markResourceName`: Existing vector mark resource.
- `backgroundColorRole`: Material color role or fixed brand-safe color.
- `message`: Safe fallback loading message.

Rules:

- Must render without the optimized bitmap.
- Must still display app name, version/build, and loading status.
- Must preserve accessibility labels.

## State Transitions

```text
Cold launch
  -> LoadScreenPresentation(routeStatus = Loading)
  -> StartupRouteState(Unauthenticated)
  -> Login

Cold launch
  -> LoadScreenPresentation(routeStatus = Loading)
  -> StartupRouteState(Authenticated)
  -> Home or safe preserved route

Cold launch
  -> LoadScreenPresentation(routeStatus = Loading)
  -> StartupRouteState(SessionExpired or Offline)
  -> Login with safe recovery state

Artwork missing or unsafe
  -> FallbackBrandState
  -> Existing startup route flow
```
