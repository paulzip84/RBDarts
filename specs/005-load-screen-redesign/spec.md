# Feature Specification: Load Screen Visual Redesign

**Feature Branch**: `005-load-screen-redesign`

**Created**: 2026-05-22

**Status**: Draft

**Input**: User description: "Create a spec for a redesigned Load Screen using the attached image."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Launch With A Full-Bleed RBDarts Brand Moment (Priority: P1)

As a player opening RBDarts, I want the load screen to use the supplied darts artwork as a polished branded first impression so that the app feels distinctive, energetic, and connected to baseball darts before I sign in or resume play.

**Why this priority**: The load screen is the first visual state of the app. It sets trust and product tone before the user reaches login, home, or scoring.

**Independent Test**: Cold launch the Android app and verify the load screen displays the supplied image or an optimized derivative, `RBDarts`, version/build information, and a clear loading state before routing to login or authenticated home.

**Acceptance Scenarios**:

1. **Given** the app is launched from a stopped state, **When** the load screen appears, **Then** the screen uses the attached RBDarts darts image as the primary visual asset or visual source.
2. **Given** the load screen is visible, **When** the user views it on a compact phone, **Then** `RBDarts`, version/build, and loading status remain readable over the image.
3. **Given** initial session routing completes, **When** the app is ready, **Then** the load screen transitions to login or authenticated home without leaving the user on a blank or stale screen.

---

### User Story 2 - Keep The Image Safe, Performant, And Adaptive (Priority: P2)

As a user on different Android devices, I want the image-based load screen to look intentional and load quickly so that app startup feels stable instead of heavy or cropped awkwardly.

**Why this priority**: The attached PNG is large and visually detailed. It must be optimized, cropped, and rendered safely for startup performance and multiple device sizes.

**Independent Test**: Launch on representative compact, large phone, and tablet/foldable viewports and verify the artwork frames the dartboard/mascot area well, loads quickly, and does not degrade accessibility or routing.

**Acceptance Scenarios**:

1. **Given** the image asset is packaged with the app, **When** the load screen renders, **Then** no remote image fetch is required.
2. **Given** the device has a tall or wide screen, **When** the image is displayed, **Then** the crop keeps the dartboard artwork recognizable while preventing important text from overlapping busy image regions.
3. **Given** the device uses dark theme, large font, or display zoom, **When** the load screen renders, **Then** the branding and loading status remain visible and accessible.

---

### User Story 3 - Provide Accessible And Privacy-Safe Startup Feedback (Priority: P3)

As a user relying on accessibility or waiting for startup, I want the load screen to communicate app status safely so that I understand what is happening without exposing debug, account, or provider details.

**Why this priority**: Startup can involve session checks and provider state. The load screen should be reassuring and accessible, not a place where sensitive details or noisy implementation text appears.

**Independent Test**: Start the app with unauthenticated, authenticated, offline, and session-expired states and verify the load screen has screen-reader labels, privacy-safe messages, and predictable routing.

**Acceptance Scenarios**:

1. **Given** TalkBack is enabled, **When** the load screen appears, **Then** the image has an appropriate accessibility treatment and the loading status is announced in meaningful text.
2. **Given** session or provider checks fail, **When** the app routes away from loading, **Then** errors are shown on the appropriate login screen rather than exposing raw provider details on the load screen.
3. **Given** loading takes longer than expected, **When** the load screen remains visible, **Then** it continues showing a calm progress state and does not reveal debug logs, stack traces, or secrets.

### Edge Cases

- The attached image is too large for efficient startup if used directly.
- Android resource conversion changes the image color, scale, or transparency.
- Cropping cuts off the main mascot/hat/dartboard composition on compact phones.
- The image is visually busy and makes text unreadable without an overlay or scrim.
- Version/build information is missing in debug builds.
- Session routing is slow, offline, cancelled, or expires during startup.
- The app is backgrounded, rotated, or killed while the load screen is visible.
- Dynamic color or system theme creates insufficient contrast over the artwork.
- TalkBack announces decorative artwork too verbosely or misses the loading status.
- The image file contains metadata, remote references, hidden payloads, or non-launch-safe content.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The Android app MUST present a redesigned load screen before routing to login or authenticated home.
- **FR-002**: The load screen MUST use the attached `RBDarts_Login` PNG as the visual source for the primary load-screen artwork.
- **FR-003**: The app MUST package an optimized local image derivative suitable for Android startup rendering and MUST NOT fetch the load image from the network at runtime.
- **FR-004**: The load screen MUST display `RBDarts` and the current app version/build in a readable layout.
- **FR-005**: The load screen MUST display a clear loading/progress state while startup, session, or routing checks are in progress.
- **FR-006**: The visual design MUST preserve the recognizable dartboard, red dart flights, and white illustrated RBDarts mascot/mark from the supplied artwork where practical.
- **FR-007**: The load screen MUST use a scrim, gradient, safe text region, or equivalent treatment to keep app name, version, and loading status readable over the image.
- **FR-008**: The artwork MUST adapt to compact phones, large phones, tablets, and foldables without critical branding or status text being clipped.
- **FR-009**: The load screen MUST route unauthenticated users to login and authenticated users to the authenticated home or safe preserved route.
- **FR-010**: The load screen MUST avoid showing provider errors, raw session details, debug text, stack traces, or implementation-only status.
- **FR-011**: The load screen MUST support dark theme, light theme, and dynamic color environments while preserving brand recognition.
- **FR-012**: The load screen MUST provide accessible labels for app name, version/build, and loading status.
- **FR-013**: Decorative artwork MUST either be hidden from assistive technology or described concisely without blocking the loading status announcement.
- **FR-014**: The implementation MUST include automated coverage for default rendering, version label, route completion, accessibility labels, and compact/adaptive layout.
- **FR-015**: The implementation MUST include manual screenshot evidence for at least one representative emulator/device.

### Security, Privacy, and Identity Requirements *(mandatory)*

- **SPR-001**: The feature MUST identify all startup, route, session, image asset, version/build, and diagnostic data displayed or touched by the load screen.
- **SPR-002**: Sensitive data MUST include SSO tokens, provider payloads, provider account identifiers, user contact details, session state, debug logs, crash payloads, and signed build metadata.
- **SPR-003**: The load screen MUST NOT collect credentials, provider secrets, or first-party passwords.
- **SPR-004**: Authentication and session decisions MUST remain delegated to existing SSO/session routing components; the load screen is presentation and routing feedback only.
- **SPR-005**: Load-screen diagnostics MUST use privacy-safe event names and coarse attributes only, excluding tokens, emails, raw provider responses, user ids, and account details.
- **SPR-006**: The attached image and optimized derivatives MUST be reviewed for metadata, embedded scripts, remote references, and hidden credential material before commit.
- **SPR-007**: The load screen MUST protect authenticated destinations from unauthenticated access after sign-out, session expiration, or failed session restoration.

### Performance and Stability Requirements *(mandatory)*

- **PSR-001**: Branded load-screen content MUST appear within one second of cold app launch on representative Android devices.
- **PSR-002**: Startup routing from load screen to login or authenticated home MUST complete within two seconds when local session state is available.
- **PSR-003**: The optimized image asset MUST avoid excessive APK bloat and MUST not cause startup memory pressure or main-thread jank.
- **PSR-004**: The load screen MUST remain stable through rotation, background/resume, dark/light theme, large font, and display zoom.
- **PSR-005**: If startup routing is delayed, the load screen MUST remain responsive and continue showing a safe loading state.
- **PSR-006**: The app MUST avoid crashes or blank screens if the optimized image asset fails to load; a branded fallback MUST be available.

### Key Entities *(include if feature involves data)*

- **Load Screen Presentation**: App name, artwork, version/build label, loading message, progress indicator, accessibility labels, and visual overlay/scrim.
- **Load Artwork Asset**: Source PNG, optimized Android derivative, dimensions, crop/focal region, metadata review status, and fallback asset.
- **Startup Route State**: Initial loading, unauthenticated route, authenticated route, session-expired route, offline/degraded route, and timeout behavior.
- **Load Diagnostic Event**: Privacy-safe startup/load event name, route result, timing bucket, and coarse failure reason where applicable.
- **Fallback Brand State**: Local non-image or smaller-image state used if the primary image cannot be loaded safely.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 95% of cold launches show the redesigned branded load screen within one second on representative Android devices.
- **SC-002**: 95% of local-session launches route from load screen to login or authenticated home within two seconds.
- **SC-003**: 100% of reviewed load-screen variants keep `RBDarts`, version/build, and loading status readable on compact phone, large phone, and tablet/foldable viewports.
- **SC-004**: Accessibility smoke checks pass for TalkBack labels, large font, display zoom, and dark/light theme.
- **SC-005**: Asset review confirms the packaged image derivative contains no scripts, remote runtime references, credentials, or unsafe metadata.
- **SC-006**: Android unit, Compose UI source compilation, lint, APK build, and emulator smoke pass before implementation is accepted.

## Assumptions

- This feature is scoped to the Android Kotlin/Jetpack Compose load screen.
- The attached file `/Users/paulzip84/Downloads/RBDarts_Login` is the intended visual source for the redesigned load screen.
- The existing loading route, release configuration, app version/build source, and auth routing from `003-android-material-you-ui` remain available.
- The source PNG may need optimization, resizing, or conversion before being packaged in the Android app.
- iOS parity, provider configuration changes, backend changes, and login-page redesign changes are out of scope for this feature.
