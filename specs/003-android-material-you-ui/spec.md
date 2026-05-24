# Feature Specification: Android Material You App Experience

**Feature Branch**: `003-android-material-you-ui`

**Created**: 2026-05-22

**Status**: Draft

**Input**: User description: "Change the UI of the Kotlin Android app. Introduce a new loading page with the attached image with RBDarts and version number. Add a new login page to either sign up/sign in with Google or Facebook SSO and appropriate navigation. Add sections for Game type, Player creation, Season Creation, handicap management at the individual player level and a robust scoring section. Use Material You design and best practices. Research popular apps that use Material You components as examples."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Launch And Authenticate With Branded Material You Flow (Priority: P1)

As a player opening the Android app, I want to see a polished RBDarts loading screen and then sign in or sign up with Google or Facebook SSO so that the app feels trustworthy, branded, and ready for personal scoring data.

**Why this priority**: Authentication and first impression gate every authenticated game, season, player, and scoring workflow. The app cannot feel launch-ready if users land directly in a utility form without branding or secure sign-in context.

**Independent Test**: Install the Android app, open it from a cold start, verify the loading screen shows the provided image, app name, and version, then complete or cancel Google/Facebook SSO and confirm the user lands in the correct authenticated or unauthenticated destination.

**Acceptance Scenarios**:

1. **Given** the Android app is launched from a stopped state, **When** initial app state is loading, **Then** the app displays the provided RBDarts image, "RBDarts", the current app version/build, and an accessible loading state without exposing development or placeholder text.
2. **Given** the user is not authenticated, **When** loading completes, **Then** the app presents a Material You login page with Google and Facebook sign-in/sign-up actions and clear privacy/support access.
3. **Given** the user selects a provider, **When** sign-in succeeds, **Then** the app navigates to the authenticated home experience without requiring first-party password creation.
4. **Given** the user cancels sign-in or a provider fails, **When** the app returns from the provider flow, **Then** the login page remains usable, explains the failure in a privacy-safe way, and does not lose local draft game setup state.

---

### User Story 2 - Navigate Core Baseball Darts Sections (Priority: P2)

As an authenticated player or scorekeeper, I want clear navigation between game type, players, seasons, handicaps, and scoring so that I can set up the right darts context without hunting through disconnected screens.

**Why this priority**: The requested UI is not just visual polish; it changes the app from a single scoring form into a structured Android experience that supports multiple game and league workflows.

**Independent Test**: Sign in, use the primary navigation to visit every requested section, return to home, and verify selected section state survives rotation, background/resume, and back navigation.

**Acceptance Scenarios**:

1. **Given** an authenticated user is on the Android home experience, **When** they use primary navigation, **Then** they can reach Game Type, Players, Seasons, Handicaps, Scoring, Stats or Summary, and Settings/Privacy sections.
2. **Given** the user is on any core section, **When** they press system back, use bottom navigation, or use a top app bar action, **Then** navigation follows Android expectations and never strands the user on an empty or inaccessible screen.
3. **Given** the app runs on compact and expanded Android screens, **When** the layout changes size class or orientation, **Then** navigation adapts without hiding core actions or truncating essential labels.

---

### User Story 3 - Create Players And Seasons Before Scoring (Priority: P3)

As a league manager or scorekeeper, I want player creation and season creation screens that capture the information needed for official and casual play so that scoring starts from accurate participants, dates, and league context.

**Why this priority**: Robust scoring depends on correct player and season setup, especially when handicaps and league records are involved.

**Independent Test**: Create players, create a season, assign or select participants for a game, and verify the created entities are visible to scoring and handicap screens without duplicate entry.

**Acceptance Scenarios**:

1. **Given** an authenticated user opens Player Creation, **When** they enter valid player details, **Then** the player is saved, shown in selectable lists, and available for handicap management.
2. **Given** an authenticated user opens Season Creation, **When** they enter season name, dates, rules context, and status, **Then** the season is saved and available for game setup.
3. **Given** required fields are missing or invalid, **When** the user tries to save, **Then** the UI explains what must be fixed and keeps all valid inputs.

---

### User Story 4 - Manage Individual Player Handicaps (Priority: P4)

As a league manager or authorized scorekeeper, I want to manage handicaps at the individual player level so that baseball darts games can apply fair scoring rules based on each participant's current average or configured adjustment.

**Why this priority**: Handicap management is a central requested section and directly affects scoring trust, match fairness, and league record integrity.

**Independent Test**: Select a player, view current handicap inputs and derived values, update an authorized handicap setting, and verify the scoring setup uses the updated player-level value.

**Acceptance Scenarios**:

1. **Given** a player exists, **When** an authorized user opens Handicap Management, **Then** the app displays the player's current average, configured handicap inputs, derived handicap result, last updated timestamp, and edit authority.
2. **Given** a user updates a player handicap, **When** they save, **Then** the app validates the value, records who changed it, and updates future scoring setup without silently changing completed scorecards.
3. **Given** a user lacks permission to edit handicaps, **When** they open the section, **Then** they can view approved values but cannot save changes.

---

### User Story 5 - Score Baseball Darts With A Robust Material You Interface (Priority: P5)

As a scorekeeper, I want a robust scoring section optimized for fast inning-by-inning entry, corrections, totals, handicaps, and recovery so that I can run a match confidently on an Android phone or tablet.

**Why this priority**: Scoring is the core product value. The UI redesign must improve, not weaken, speed, clarity, and stability during live play.

**Independent Test**: Start a game from selected type, players, season, and handicap context; enter valid and invalid inning scores; review totals and handicap effects; background/restart; complete the game; and verify the final scorecard.

**Acceptance Scenarios**:

1. **Given** a game setup has selected game type and players, **When** scoring starts, **Then** the scoring screen displays innings, active target, participants, current scores, running totals, handicap-adjusted totals where applicable, and clear save/progress state.
2. **Given** the scorekeeper enters invalid values, **When** they submit, **Then** the scoring UI rejects the value, preserves the prior valid state, and indicates the allowed range.
3. **Given** the app is interrupted during scoring, **When** it resumes, **Then** the most recent valid scoring state is restored and the user can continue without duplicate score submission.

### Edge Cases

- The attached SVG cannot be directly rendered by Android without conversion, optimization, or replacement by an Android-compatible image asset.
- The app version/build cannot be read or is missing in debug builds.
- Google or Facebook SSO is unavailable, cancelled, returns an expired session, or returns a profile with incomplete display data.
- The user launches the app with no network connection and no active session.
- A user signs out while game setup or score entry has unsaved local changes.
- Navigation deep links or restored back stacks point to authenticated screens after session expiration.
- A player is deleted, merged, or deactivated while a draft game references that player.
- A season date range overlaps another season or is created without enough rule context for scoring.
- A handicap update would affect a completed game or locked match.
- A scoring session has more players than fit comfortably on a compact phone screen.
- The user changes device font scale, display size, orientation, theme, or dynamic color while scoring.
- TalkBack focus order, touch target size, or contrast makes score entry difficult.
- The app is backgrounded, killed, or loses connectivity immediately after a score entry.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The Android app MUST present a branded loading screen before routing to login or authenticated home.
- **FR-002**: The loading screen MUST display the provided RBDarts image asset, the text "RBDarts", and the current app version/build in a readable and accessible layout.
- **FR-003**: The app MUST route unauthenticated users to a login page after loading completes.
- **FR-004**: The login page MUST support sign up/sign in with Google SSO and Facebook SSO and MUST NOT introduce first-party password creation.
- **FR-005**: The login page MUST include privacy/support access and a clear recovery path for provider cancellation or failure.
- **FR-006**: Authenticated navigation MUST expose top-level sections for Game Type, Player Creation, Season Creation, Handicap Management, Scoring, Stats or Summary, and Settings/Privacy.
- **FR-007**: Navigation MUST preserve each section's state across back navigation, orientation change, and background/resume where data has not been explicitly discarded.
- **FR-008**: The Game Type section MUST allow users to select from supported baseball darts modes and surface whether the selection is standalone, season-linked, league-linked, handicap-enabled, or practice-oriented.
- **FR-009**: The Player Creation section MUST allow users to create, view, and select players needed by game setup, scoring, and handicap workflows.
- **FR-010**: The Season Creation section MUST allow users to create and view season context used by league or recurring play.
- **FR-011**: The Handicap Management section MUST manage handicap values at the individual player level and show derived scoring impact before those values are applied.
- **FR-012**: Handicap changes MUST be permission-aware and MUST NOT silently mutate completed or locked scorecards.
- **FR-013**: The Scoring section MUST support inning-by-inning baseball darts score entry, validation, running totals, handicap-adjusted totals where applicable, completion, and scorecard review.
- **FR-014**: The Scoring section MUST support fast entry on compact phones and clear review on larger Android screens without hiding critical totals or controls.
- **FR-015**: The redesigned UI MUST follow Material You expectations for color, typography, shape, motion, state, component behavior, and accessible touch targets.
- **FR-016**: The app MUST support light theme, dark theme, and Android dynamic color where available while preserving RBDarts brand recognizability.
- **FR-017**: The app MUST use Android-appropriate navigation patterns for compact, medium, and expanded layouts.
- **FR-018**: Every primary action MUST have loading, success, empty, validation-error, provider-error, offline, and retry states where applicable.
- **FR-019**: The redesign MUST avoid in-app instructional marketing copy and instead make workflows discoverable through clear labels, hierarchy, and Material component affordances.
- **FR-020**: The app MUST include Android UI smoke coverage for loading, login, navigation, player, season, handicap, and scoring surfaces.

### Security, Privacy, and Identity Requirements *(mandatory)*

- **SPR-001**: The feature MUST identify all account, session, player, season, handicap, game setup, score entry, correction, and diagnostic data displayed or changed by the redesigned UI.
- **SPR-002**: Sensitive data MUST include SSO identity, provider identifiers, session state, player profile details, season membership, handicap values, score history, correction rationale, and diagnostics linked to a user or game.
- **SPR-003**: Authentication MUST continue to use trusted SSO providers only; no first-party password flow may be introduced.
- **SPR-004**: The login flow MUST define user-visible behavior for sign-in, sign-out, provider failure, cancellation, session expiration, and account recovery.
- **SPR-005**: The app MUST not log SSO tokens, raw provider responses, user contact details, private player notes, or correction rationale through UI diagnostics.
- **SPR-006**: Navigation MUST protect authenticated screens from unauthenticated access after sign-out or session expiration.
- **SPR-007**: Player handicap edits MUST be permission-aware and auditable before they affect future scoring.
- **SPR-008**: The loading image asset MUST be committed as a non-secret static asset and MUST NOT embed external tracking, remote references, scripts, or hidden credential material.

### Performance and Stability Requirements *(mandatory)*

- **PSR-001**: Cold launch MUST show the branded loading screen quickly enough that users are not left on a blank screen for more than one second on representative devices.
- **PSR-002**: Auth routing from loading to login or authenticated home MUST complete within two seconds when local session state is available.
- **PSR-003**: Primary navigation transitions MUST feel immediate and avoid visible jank during normal use.
- **PSR-004**: Score entry MUST accept valid inning input and update visible totals within 250 ms on representative devices.
- **PSR-005**: In-progress scoring, player creation, season creation, and handicap edits MUST survive app background/resume and configuration changes until saved or explicitly discarded.
- **PSR-006**: The redesigned UI MUST maintain accessible text, touch targets, focus order, and contrast under large font and dark theme settings.
- **PSR-007**: The app MUST avoid crashes or data loss when SSO providers fail, network connectivity changes, or Android destroys and recreates the activity.

### Key Entities *(include if feature involves data)*

- **Launch Presentation**: The initial loading state, branded image, app name, version/build, routing status, and accessibility label.
- **Authentication Session**: Current login state, selected provider, user-visible profile summary, provider error state, sign-out state, and recovery path.
- **Navigation Destination**: A top-level or nested app section with route identity, selected state, saved state, auth requirement, and adaptive presentation.
- **Game Type**: A selectable baseball darts mode with scoring rules, player requirements, season linkage, handicap behavior, and practice/league flags.
- **Player Profile**: A participant record with display name, status, optional team/season relationship, seed average, current average, and handicap references.
- **Season**: A recurring play context with name, date range, status, rules summary, eligible players, and game type constraints.
- **Player Handicap**: A player-level scoring adjustment with source average, configured value, derived result, effective date, editor, and audit status.
- **Scoring Session**: An active or completed game with participants, innings, targets, score entries, validation state, totals, handicap-adjusted totals, recovery state, and completion status.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 95% of test launches show branded loading content within one second on representative Android devices.
- **SC-002**: 95% of authenticated test sessions can reach every top-level section from primary navigation in three taps or fewer.
- **SC-003**: 90% of first-time test users can sign in or identify how to sign in with Google/Facebook without assistance.
- **SC-004**: 90% of test scorekeepers can create players, create or select a season, assign player handicaps, start scoring, and complete a game without leaving the primary flow.
- **SC-005**: Score entry updates visible totals within 250 ms in manual QA and automated performance smoke checks.
- **SC-006**: Accessibility smoke checks pass for TalkBack labels, focus order, large font, dark theme, and minimum touch target coverage on primary screens.
- **SC-007**: Android unit, UI, and lint verification pass with no high-severity UI, auth routing, navigation, or scoring regressions.

## Assumptions

- The existing Kotlin/Jetpack Compose Android app remains the implementation target for this feature.
- Google and Facebook remain the only MVP SSO providers.
- The attached SVG at `/Users/paulzip84/Downloads/Gemini_Generated_Image_g6jmlyg6jmlyg6jm.svg` is the intended visual source for the loading screen and may be converted to an Android-compatible static asset during implementation.
- The Android app version/build shown on the loading screen comes from Android package metadata or the existing release configuration.
- This feature is scoped to Android UI/UX and navigation; iOS parity can be handled by a separate feature.
- Backend service changes are out of scope unless needed to persist player, season, handicap, or scoring data already modeled by the app.
- Existing security-first project principles remain binding: no committed secrets, no first-party passwords, privacy-safe diagnostics, and recoverable scoring state.
