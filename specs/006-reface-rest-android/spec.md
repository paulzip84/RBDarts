# Feature Specification: Authenticated Android App Shell Redesign

**Feature Branch**: `006-reface-rest-android`

**Created**: 2026-05-22

**Status**: Draft

**Input**: User description: "Create a new requirement to reface the rest of the app after the load page and login pages which are already redesigned. Please redesign the app during the same dark mode theme, colors and style of the login page. Adjust the navigation on the bottom into a hamburger button pattern with the hamburger navigation button at the top left of the app. Please let me know if a research effort is needed in the speckit workflow after the requirement is created."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Use A Unified Premium Dark App Shell (Priority: P1)

As an authenticated RBDarts player, I want every post-login screen to share the same premium dark visual language as the redesigned login page so that the app feels cohesive after I leave loading and sign-in.

**Why this priority**: The load screen and login page now define the brand tone. The rest of the app should not feel like a different product after authentication.

**Independent Test**: Sign in or render the authenticated shell and verify Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings use the dark login-inspired colors, typography, surfaces, spacing, and button styling without changing the already-redesigned loading or login pages.

**Acceptance Scenarios**:

1. **Given** the user is authenticated, **When** the Home dashboard appears, **Then** it uses the same dark premium theme direction as the redesigned login page.
2. **Given** the user navigates to Game Type, Players, Seasons, Handicaps, Scoring, or Settings, **When** each screen renders, **Then** each screen uses consistent dark surfaces, text color, accent color, and component styling.
3. **Given** the app returns from loading or login, **When** the authenticated experience appears, **Then** the transition feels visually continuous and does not revert to the older bright or generic Material screen style.

---

### User Story 2 - Navigate With A Top-Left Hamburger Menu (Priority: P1)

As an authenticated user, I want the bottom navigation replaced with a hamburger button at the top left so that primary sections live in a compact app menu that matches the redesigned app style.

**Why this priority**: The requested navigation pattern is a core structural change and affects every post-login workflow.

**Independent Test**: On authenticated screens, verify there is no bottom navigation bar, the top-left hamburger button is visible, and opening it exposes all primary app sections with clear selected-state behavior.

**Acceptance Scenarios**:

1. **Given** the user is on an authenticated screen, **When** the screen loads, **Then** a hamburger menu button appears at the top left.
2. **Given** the hamburger button is visible, **When** the user activates it, **Then** a navigation menu opens with Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, and Sign Out.
3. **Given** the navigation menu is open, **When** the user selects a destination, **Then** the app navigates to that section, closes the menu, and marks the selected destination clearly.
4. **Given** the authenticated shell is visible, **When** the user looks at the bottom of the screen, **Then** no bottom navigation bar is present.

---

### User Story 3 - Preserve Efficient Darts Workflows (Priority: P2)

As a player using the app during setup or live scoring, I want the redesigned screens to remain fast and easy to scan so that the darker visual style does not slow down player creation, season setup, handicap edits, or scoring.

**Why this priority**: Visual consistency cannot come at the cost of live-play speed, especially in scoring and handicap workflows.

**Independent Test**: Complete representative flows for game type selection, player creation, season creation, handicap adjustment, and scoring while verifying the new app shell does not hide primary actions or add unnecessary steps.

**Acceptance Scenarios**:

1. **Given** the user is creating players or seasons, **When** forms are displayed, **Then** inputs, labels, validation messages, and primary actions remain readable in the dark style.
2. **Given** the user is editing handicaps, **When** changing an individual player handicap, **Then** the player-level status and save action remain obvious.
3. **Given** the user is scoring a game, **When** entering and submitting scores, **Then** scoring controls remain prominent, reachable, and responsive.

---

### User Story 4 - Support Accessibility And Adaptive Layouts (Priority: P3)

As a user on different devices or accessibility settings, I want the hamburger app shell and redesigned screens to work with TalkBack, large font, display zoom, rotation, and compact or expanded widths.

**Why this priority**: Navigation and dark surfaces must remain usable for all supported Android users and devices.

**Independent Test**: Render the authenticated shell and primary screens in compact, expanded, dark theme, large font, and TalkBack scenarios and verify navigation, text, actions, and focus order remain usable.

**Acceptance Scenarios**:

1. **Given** TalkBack is enabled, **When** the user reaches the hamburger button, **Then** it has a clear label and opens a menu with accessible destination names.
2. **Given** large font or display zoom is enabled, **When** primary screens render, **Then** text and controls do not overlap, clip, or become unreachable.
3. **Given** a tablet, foldable, or landscape layout, **When** the app shell renders, **Then** the hamburger pattern adapts intentionally without restoring the old bottom navigation.

### Edge Cases

- A user is on a protected section and signs out from the hamburger menu.
- The app restores a protected route after process death or rotation.
- The hamburger menu is opened while a form has unsaved data.
- A scoring entry is in progress while navigation is opened.
- A destination name is too long for compact width, large font, or display zoom.
- The selected destination changes while the menu is open.
- The app is offline while navigating between local setup and scoring screens.
- Dark surfaces reduce contrast for disabled controls, validation errors, or secondary text.
- Android back is pressed while the hamburger menu is open.
- A screen previously using bottom navigation assumes persistent bottom padding.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The Android authenticated app experience MUST be visually redesigned to match the dark premium style, color direction, spacing, and component tone established by the redesigned login page.
- **FR-002**: The redesigned loading screen and redesigned login page MUST remain in scope only as visual references and MUST NOT be reworked by this feature unless needed to preserve handoff consistency.
- **FR-003**: The authenticated shell MUST remove the existing bottom navigation pattern from post-login screens.
- **FR-004**: The authenticated shell MUST provide a hamburger navigation button at the top left of authenticated screens.
- **FR-005**: Activating the hamburger button MUST reveal a navigation menu containing Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, and Sign Out.
- **FR-006**: The navigation menu MUST show the current selected destination and close after a destination is selected.
- **FR-007**: The navigation menu MUST route protected destinations only for authenticated users and MUST route unauthenticated or signed-out users back to login.
- **FR-008**: Home, Game Type, Player creation, Season creation, Handicap management, Scoring, and Settings screens MUST use a coherent dark visual system based on the redesigned login page.
- **FR-009**: Primary actions, destructive actions, disabled states, empty states, validation messages, and error messages MUST be restyled for the dark theme while preserving meaning.
- **FR-010**: Scoring controls MUST remain visually prominent and usable with one-handed phone interaction where practical.
- **FR-011**: Player creation, season creation, and handicap forms MUST keep labels, values, validation messages, and save actions readable in the dark style.
- **FR-012**: The app shell MUST preserve existing authenticated navigation destinations and sign-out behavior.
- **FR-013**: The app shell MUST support Android back behavior: close the hamburger menu first, then apply normal screen/back behavior.
- **FR-014**: The implementation MUST include automated coverage for hamburger visibility, no bottom navigation, menu open/close, destination selection, protected route behavior, dark styling, accessibility labels, compact layout, expanded layout, large font, and representative workflow screens.
- **FR-015**: The implementation MUST include manual screenshot evidence for representative authenticated screens using the new dark hamburger app shell.

### Security, Privacy, and Identity Requirements *(mandatory)*

- **SPR-001**: The feature MUST identify all profile, player, season, handicap, scoring, route, and diagnostic data displayed or touched by the authenticated app shell and redesigned screens.
- **SPR-002**: Sensitive data MUST include SSO tokens, provider payloads, account identifiers, user contact details, session state, signed build metadata, private player notes, and any unreleased scoring or season records.
- **SPR-003**: The hamburger menu MUST NOT expose SSO tokens, provider identifiers, email addresses, raw session details, debug state, stack traces, or hidden route parameters.
- **SPR-004**: Sign out from the hamburger menu MUST clear authenticated UI access and return to login without leaving protected content visible.
- **SPR-005**: Navigation diagnostics MUST use privacy-safe event names and coarse destination names only, excluding user identifiers, player names, emails, provider payloads, tokens, raw scores, or session ids.
- **SPR-006**: Protected destination routing MUST continue to enforce authentication after app restart, sign-out, session expiration, and failed session restoration.

### Performance and Stability Requirements *(mandatory)*

- **PSR-001**: Hamburger menu open and close interactions MUST feel immediate and complete within 250 ms on representative Android devices.
- **PSR-002**: Navigation between local authenticated sections MUST complete within 500 ms when no network work is required.
- **PSR-003**: Scoring entry interactions MUST remain responsive and avoid added input latency from the redesigned app shell.
- **PSR-004**: The redesigned app shell MUST remain stable through rotation, background/resume, process recreation, large font, display zoom, and dark theme.
- **PSR-005**: The redesign MUST avoid avoidable APK bloat, main-thread jank, unbounded recomposition, or memory growth from decorative visuals.
- **PSR-006**: If navigation or screen state restoration fails, the app MUST recover to a safe authenticated home or login state without exposing protected content to unauthenticated users.

### Key Entities *(include if feature involves data)*

- **Authenticated App Shell**: The post-login frame containing top app controls, hamburger trigger, current destination title, content area, and sign-out access.
- **Hamburger Navigation Menu**: The opened navigation surface containing destinations, selected state, menu actions, accessibility labels, and close behavior.
- **Authenticated Destination**: A protected app section such as Home, Game Type, Players, Seasons, Handicaps, Scoring, or Settings.
- **Dark App Design System**: Login-inspired color roles, typography usage, surfaces, controls, validation states, error states, and spacing used across authenticated screens.
- **Workflow Screen State**: Screen-specific state for player creation, season creation, handicap management, and scoring that must survive navigation and configuration changes where applicable.
- **Navigation Diagnostic Event**: Privacy-safe event for menu opened, destination selected, sign out selected, or protected route redirected.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of authenticated primary screens use the new dark app-shell styling and no longer show the old bottom navigation.
- **SC-002**: 100% of authenticated primary screens expose a top-left hamburger navigation button with accessible text.
- **SC-003**: Users can navigate from Home to every primary destination and back through the hamburger menu in no more than two actions per destination.
- **SC-004**: Representative player creation, season creation, handicap edit, and scoring workflows remain completable without visual overlap or missing primary actions on compact phone and expanded layouts.
- **SC-005**: Accessibility checks pass for hamburger button labeling, menu destination labels, selected-state announcement, large font, display zoom, and dark contrast.
- **SC-006**: Android unit tests, Compose UI source compilation, lint, APK build, and manual screenshot smoke pass before implementation is accepted.

## Assumptions

- This feature is scoped to the Android Kotlin/Jetpack Compose authenticated experience.
- The redesigned loading screen from `005-load-screen-redesign` and login page from `004-login-page-redesign` are considered already redesigned and should be preserved.
- Existing Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, and Sign Out destinations remain the primary authenticated navigation set.
- The requested "hamburger button pattern" may be implemented as an Android-appropriate navigation drawer, modal drawer, or equivalent top-left menu pattern during planning.
- iOS parity, backend changes, SSO provider configuration, first-party credentials, and new gameplay rules are out of scope for this feature.
