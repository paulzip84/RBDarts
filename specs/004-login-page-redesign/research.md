# Research: Login Page Visual Redesign

Feature: `004-login-page-redesign`

## Decision 1: Treat The Reference Image As Visual Direction, Not Credential UX

**Decision**: Use the attached image for dark premium tone, centered brand mark, hierarchy, rounded login surface, visual divider, and stacked provider actions. Do not copy email/password fields or "Forgot Password?".

**Rationale**: RBDarts constitution requires trusted SSO identity and explicitly avoids first-party passwords. Copying the reference literally would create a new password collection surface and violate the security model.

**Alternatives Considered**:

- Copy the full image literally: rejected because it introduces email/password and password recovery.
- Keep the current plain login screen: rejected because it does not satisfy the requested redesign or premium visual direction.

## Decision 2: Make Dark Premium Treatment Primary With Material You Fallbacks

**Decision**: Design the login screen around a dark, high-contrast surface inspired by the reference while still using Material You color roles and supporting light theme/dynamic color.

**Rationale**: The reference image is dark and atmospheric. Android users also expect dynamic color and theme behavior, so the design should be brand-forward without becoming brittle under user-selected themes.

**Alternatives Considered**:

- Force dark mode only: rejected because it conflicts with Android user preference and accessibility expectations.
- Use generic Material light screen only: rejected because it loses the supplied design direction.

## Decision 3: Add A Login Presentation State Model

**Decision**: Add a small UI state contract for login presentation, provider actions, support links, and error banners.

**Rationale**: The current `AuthUiState` captures auth status but not provider button availability, offline/session-expired copy, support link prominence, or visual presentation variants. A dedicated presentation model makes tests easier and keeps UI rules explicit.

**Alternatives Considered**:

- Encode all states directly in composables: rejected because it makes provider failure and accessibility states harder to test.
- Expand domain auth models with UI-only design fields: rejected because visual state should not leak into identity abstractions.

## Decision 4: Keep Provider Actions To Google And Facebook

**Decision**: MVP provider buttons remain Google and Facebook only.

**Rationale**: The project spec and existing app are already scoped to Google/Facebook SSO. The reference image includes Microsoft and Okta, but adding providers requires separate identity, brand, policy, and provider-console work.

**Alternatives Considered**:

- Add Microsoft/Okta provider placeholders: rejected because unavailable providers can confuse users and expand auth scope without implementation.

## Decision 5: Convert Password Recovery Into Provider-Safe Help

**Decision**: Replace any "Forgot Password?" concept with "Need help signing in?" that routes to support/account access information.

**Rationale**: RBDarts cannot reset Google or Facebook credentials. The help path should explain provider-based recovery and provide support/privacy/account deletion links.

**Alternatives Considered**:

- Hide all help: rejected because provider failures need a recovery path.
- Link to provider password reset pages directly: deferred because provider-specific recovery links require policy and localization review.

## Decision 6: Test Design Quality Through Compose UI Smoke

**Decision**: Require Compose UI tests for default, loading, failure, offline, dark theme, large font/adaptive layout, and accessibility labels. Manual emulator smoke remains required for screenshot evidence.

**Rationale**: The main risk is not complex backend behavior; it is visual regressions, clipped labels, confusing provider states, and accidental password UX. Compose UI tests are the most direct guardrail.

**Alternatives Considered**:

- Unit tests only: rejected because layout and semantics are central to this feature.
- Screenshot-only verification: rejected because it is useful evidence but weak at asserting behavior.
