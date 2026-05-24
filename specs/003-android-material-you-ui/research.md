# Research: Android Material You App Experience

**Feature**: `003-android-material-you-ui`
**Date**: 2026-05-22

## Research Goals

- Determine the Material You guidance that should shape the Android redesign.
- Identify popular or official Android app examples that use Material You patterns.
- Decide how the current RBDarts Kotlin app should plan navigation, authentication, setup, handicap, and scoring surfaces.

## Sources Reviewed

- [Android Developers: Material Design 3 in Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
- [Material Design 3 component catalog](https://m3.material.io/components)
- [Android Developers: Navigation with Compose](https://developer.android.com/develop/ui/compose/navigation)
- [Android Developers: App architecture guide](https://developer.android.com/topic/architecture)
- [Android Developers Blog: Material Design 3 for Compose hits stable](https://android-developers.googleblog.com/2022/10/material-design-3-compose-stable.html)
- [Android Developers Blog: Material You coming to more Android devices](https://android-developers.googleblog.com/2022/02/material-you-coming-to-more-android.html)
- [Android Developers Blog: What's new in Jetpack Compose](https://android-developers.googleblog.com/2023/05/whats-new-in-jetpack-compose.html)
- [Android Developers Blog: Google Contacts widget with Material You dynamic theming](https://android-developers.googleblog.com/2023/10/googles-contacts-app-created-new-widget-faster-using-jetpack-glance.html)

## Findings

### Material You And Material 3

Material 3 is the modern Android design system for Compose apps. It includes dynamic color, updated components, light/dark schemes, shape scale, typography, window insets behavior, and adaptive patterns. Dynamic color is available on Android 12 and above, with custom brand fallback schemes needed for older or non-dynamic devices.

**Decision**: RBDarts Android UI will use Material 3 components, dynamic color where available, a branded fallback color scheme, light/dark theme support, and accessible stateful components.

**Rationale**: This aligns the app with Android visual expectations while keeping RBDarts recognizable and testable.

### Popular App Examples

Official Android blog examples call out Google apps such as Gmail, Photos, Chrome, and Contacts using Material You/dynamic-color guidance. The Android Developers blog also references the Reply sample as a fully Material 3 Compose sample for adaptive layouts and dynamic theming.

**Decision**: Use these examples as design inspiration, not as copies:

- Gmail: clear branded identity with Material You color adaptation and high-density workflow navigation.
- Photos: visual-first brand surface with simple, confidence-building navigation.
- Chrome: large-scale dynamic color adoption with attention to surface color and elevation.
- Contacts: personal-data workflows that benefit from clear identity, dynamic theming, and concise primary actions.
- Reply sample: Compose Material 3, adaptive navigation, and window-size-aware layout patterns.

**Rationale**: RBDarts needs a work-focused scoring UI, not a marketing surface. These examples support practical Material You choices for identity, navigation, forms, and responsive layouts.

### Loading And Branding

The requested loading page needs the provided image, app name, and version/build. The attached file is an SVG source, while Android may require conversion to a vector drawable or static raster asset depending on SVG complexity.

**Decision**: Plan to preserve the source image as design input and convert it into an Android-compatible asset during implementation. Loading must avoid blank screens, external asset loading, or hidden remote dependencies.

**Rationale**: A committed static asset is reliable, private, offline-safe, and compatible with launch-readiness constraints.

### Authentication

The project constitution and launch spec require trusted SSO and no first-party password flow. The UI should make Google and Facebook sign-in/sign-up clear while preserving provider cancellation and failure states.

**Decision**: Plan a dedicated login route after loading for unauthenticated users, with Google and Facebook actions, privacy/support links, provider error messaging, and route protection for authenticated destinations.

**Rationale**: Separating loading from login makes startup behavior testable and avoids scattering auth controls across scoring screens.

### Navigation

Android Compose Navigation supports composable destinations and navigation graph/controller patterns. Material 3 supplies navigation bar, drawer, rail, tabs, app bars, and scaffold components. Adaptive layouts should consider display size and form factor.

**Decision**: Use a single authenticated app shell with adaptive Material navigation:

- Compact: bottom navigation or navigation bar for the most important sections.
- Medium/expanded: navigation rail or drawer where appropriate.
- Top app bar: contextual title, account/settings actions, and section actions.

**Rationale**: The requested sections are more than one screen. A shell gives RBDarts coherent routing, back behavior, and state restoration.

### Android Architecture

Android architecture guidance emphasizes driving UI from data models, single sources of truth, and robust data flows that survive network loss and process death.

**Decision**: Plan UI state holders per major destination, immutable UI state, explicit UI events, repository/service-backed data, and saved state for in-progress forms and scoring.

**Rationale**: Scoring, handicap, season, and player forms are stateful and should remain stable across lifecycle events.

## Risks And Open Items

- The attached SVG may need conversion or simplification before Android can render it reliably.
- Current app may need deeper navigation refactoring than a simple screen addition.
- Real SSO flows require provider configuration and possibly debug/release SHA registration before end-to-end testing.
- Handicap authority and audit behavior may require backend/domain support if not already available.
- Scoring UI density must balance speed and accessibility on compact screens.

## Outcomes For Plan

- Build the feature as an Android-only UI/navigation redesign.
- Keep domain scoring rules and security expectations intact.
- Add explicit Android Material You theme, app shell, loading, login, setup, handicap, and scoring surfaces.
- Add UI, navigation, auth, accessibility, and performance smoke coverage before implementation is considered complete.
