# Feature Specification: Login Page Visual Redesign

**Feature Branch**: `004-login-page-redesign`

**Created**: 2026-05-22

**Status**: Draft

**Input**: User description: "Please design requirements for the login page to redesign it and use the attached image as design inspiration."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Welcome Back With A Premium Branded Login Screen (Priority: P1)

As a returning RBDarts user, I want the login page to feel polished, secure, and clearly branded so that I trust the app before I access player, season, handicap, and scoring data.

**Why this priority**: Login is the first trust boundary after launch. The screen must make RBDarts feel intentional and secure without creating password-handling risk.

**Independent Test**: Launch the Android app as an unauthenticated user, wait for loading to complete, and verify the redesigned login screen shows the RBDarts mark, a "Welcome back" heading, concise sign-in copy, SSO provider actions, and privacy/support access in a visually cohesive layout inspired by the supplied image.

**Acceptance Scenarios**:

1. **Given** the user is unauthenticated, **When** app loading completes, **Then** the login page displays a dark, branded, high-contrast welcome experience with the RBDarts mark, "Welcome back", and concise sign-in copy.
2. **Given** the login page is displayed, **When** the user scans the screen, **Then** the primary path is unmistakably SSO sign-in and no first-party password fields are shown.
3. **Given** the user uses large font or dark theme, **When** the login page renders, **Then** text, buttons, dividers, and support links remain readable and do not overlap.

---

### User Story 2 - Sign In With Trusted SSO Providers (Priority: P2)

As a user, I want to sign in with trusted identity providers from a clean provider list so that I do not need to create or remember a RBDarts password.

**Why this priority**: RBDarts is governed by SSO-only identity. The inspiration image includes first-party email/password controls, but RBDarts must preserve the safer provider-based model.

**Independent Test**: Open the login page and exercise Google and Facebook sign-in actions, including success, cancellation, provider unavailable, and retry states.

**Acceptance Scenarios**:

1. **Given** Google and Facebook sign-in are configured, **When** the user selects a provider, **Then** the app starts the provider flow and shows a loading state only for the selected provider.
2. **Given** the provider succeeds, **When** the app receives a valid session, **Then** the user lands on the authenticated home screen.
3. **Given** the provider is cancelled, unavailable, or fails, **When** the app returns to login, **Then** the page remains stable, explains the issue safely, and allows retry without exposing provider payload details.

---

### User Story 3 - Recover From Login Problems Without Password UX (Priority: P3)

As a user who cannot complete SSO, I want a clear help path so that I can recover without seeing password reset language that does not apply to RBDarts.

**Why this priority**: The reference image includes "Forgot Password?", but RBDarts does not own passwords. The redesign should convert that visual affordance into provider-safe help and account support.

**Independent Test**: Trigger provider failure and verify the screen offers privacy, support, and account deletion or account access guidance without claiming RBDarts can reset a provider password.

**Acceptance Scenarios**:

1. **Given** sign-in fails, **When** the error state appears, **Then** the user sees a privacy-safe message and a "Need help signing in?" support action.
2. **Given** the user wants privacy or account help, **When** they use login-page links, **Then** they can reach support, privacy policy, and account deletion information.
3. **Given** the app is offline, **When** the login page renders, **Then** the page explains that SSO requires connectivity and lets the user retry.

### Edge Cases

- The visual inspiration includes email/password fields and "Forgot Password?", but RBDarts must not introduce first-party credentials.
- Provider brand icons or names are unavailable, fail brand review, or require updated assets.
- Google or Facebook SSO is not configured in a debug build.
- The user has no network connection when the page appears.
- The app returns from a provider flow with cancellation, expired session, malformed response, or no display name.
- Session expires while the login page is already visible.
- Android dynamic color creates insufficient contrast on dark backgrounds.
- Large font, display zoom, TalkBack, reduced motion, or small phones cause buttons, dividers, or links to crowd.
- The loading screen and login page use different brand marks or visual language.
- Provider buttons are tapped repeatedly before the provider flow starts.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The Android login page MUST be redesigned as a premium branded welcome screen inspired by the attached dark, centered, rounded-card login design.
- **FR-002**: The screen MUST show the RBDarts mark or approved app logo near the top of the login surface.
- **FR-003**: The screen MUST include a primary heading such as "Welcome back" and concise supporting copy that explains the user is signing in to RBDarts.
- **FR-004**: The screen MUST NOT include first-party email fields, password fields, password reset actions, or any copy that implies RBDarts stores or resets user passwords.
- **FR-005**: The screen MUST support Google SSO and Facebook SSO as the MVP provider actions.
- **FR-006**: Provider actions MUST be visually grouped in a clear stack with accessible labels, loading states, disabled states, and retry-ready failure states.
- **FR-007**: If a visual divider is used between primary copy and provider actions, it MUST be meaningful for multiple SSO options and MUST remain accessible to screen readers.
- **FR-008**: The design MUST include support, privacy policy, and account deletion access without overwhelming the primary sign-in task.
- **FR-009**: The design MUST include a provider-safe help action such as "Need help signing in?" rather than "Forgot Password?".
- **FR-010**: The page MUST present provider cancellation, provider unavailable, offline, and session expired states without leaving the screen blank or blocking retry.
- **FR-011**: The login page MUST support light and dark theme, but the dark visual treatment inspired by the reference MUST be the primary design direction.
- **FR-012**: The page MUST use Material You components, motion, color roles, typography, shape, and touch targets appropriate for Android.
- **FR-013**: The page MUST adapt to compact phones, large phones, foldables, and tablets without clipped provider labels or hidden support links.
- **FR-014**: The login page MUST preserve any pre-auth local draft or routing intent that is safe to preserve when the user returns from SSO.
- **FR-015**: The redesign MUST include automated UI coverage for default, loading, error, offline, large-font, and dark-theme states.

### Security, Privacy, and Identity Requirements *(mandatory)*

- **SPR-001**: The feature MUST identify all account, provider, session, support-link, and diagnostic data displayed or changed by the login page.
- **SPR-002**: Sensitive data MUST include provider tokens, provider identifiers, session state, user contact details, display profile data, and provider error payloads.
- **SPR-003**: Authentication MUST remain SSO-only through trusted providers; RBDarts MUST NOT collect, store, validate, or transmit first-party passwords.
- **SPR-004**: Provider success, failure, cancellation, session expiration, sign-out redirection, and account support behavior MUST be specified and tested.
- **SPR-005**: Login diagnostics MUST record only privacy-safe event names and coarse provider/status attributes, excluding tokens, emails, raw provider responses, and contact details.
- **SPR-006**: Authenticated destinations MUST remain protected after sign-out or session expiration from the redesigned login path.
- **SPR-007**: Provider button assets and labels MUST follow provider brand rules and MUST NOT misrepresent the provider flow.
- **SPR-008**: The attached design inspiration MUST be treated as non-secret visual reference only and MUST NOT introduce external runtime image fetches or embedded credential material.

### Performance and Stability Requirements *(mandatory)*

- **PSR-001**: The login page MUST render visible branded content within one second after unauthenticated routing completes on representative Android devices.
- **PSR-002**: Tapping an SSO provider MUST show a selected-provider loading state within 250 ms.
- **PSR-003**: Provider failure, cancellation, or offline handling MUST return the login page to an interactive retry state without app restart.
- **PSR-004**: The login page MUST avoid layout shifts that move provider buttons after the user begins interaction.
- **PSR-005**: The screen MUST remain crash-free and usable through rotation, background/resume, dark theme, dynamic color, large font, and display zoom.
- **PSR-006**: The screen MUST avoid unnecessary network calls before the user chooses a provider, except for safe local session validation already required by auth routing.

### Key Entities *(include if feature involves data)*

- **Login Presentation**: Visual state for logo, heading, supporting copy, background treatment, divider, provider list, support links, and theme mode.
- **SSO Provider Action**: Provider identity, display label, brand asset state, availability, selected/loading state, error state, and retry eligibility.
- **Authentication Result**: Success, cancellation, failure, offline, expired session, and redirection outcome after provider interaction.
- **Account Support Link**: Privacy policy, support, account deletion, and provider-safe sign-in help destinations shown from the login page.
- **Login Diagnostic Event**: Privacy-safe event name and coarse status attributes emitted during view, provider selection, success, failure, and retry.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 95% of unauthenticated test launches display the redesigned login page within one second after loading completes.
- **SC-002**: 90% of first-time test users correctly identify that RBDarts uses SSO and does not require a password.
- **SC-003**: 95% of test users can find Google and Facebook sign-in actions without scrolling on representative compact phones.
- **SC-004**: Accessibility smoke checks pass for TalkBack labels, 200% font scaling, dark theme contrast, and minimum 48dp touch targets.
- **SC-005**: Provider cancellation, unavailable, offline, and retry states pass automated UI tests and manual emulator smoke.
- **SC-006**: No high-severity security, privacy, identity, or accessibility issues remain open before implementation is accepted.

## Assumptions

- This feature is scoped to the Android Kotlin/Jetpack Compose login page created by `003-android-material-you-ui`.
- The attached image is visual inspiration for layout, tone, spacing, dark treatment, and provider-button grouping, not a literal requirement to copy email/password UX.
- Google and Facebook remain the MVP SSO providers for RBDarts.
- Microsoft, Okta, email/password, and password reset are out of scope unless a future spec updates identity requirements and security review.
- The existing loading screen, release configuration, privacy/support links, and auth provider abstraction remain available to the login redesign.
- Production provider configuration may be absent in local debug builds, so safe unavailable/failure states are required.
