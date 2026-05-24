# Data Model: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## AuthenticatedShellState

Represents the display-safe state needed by the authenticated app frame.

Fields:

- `currentRoute`: Current `RBDartsRoute`.
- `title`: Display title for the current destination.
- `isDrawerOpen`: Whether the hamburger navigation menu is visible.
- `menuButtonLabel`: Accessible label, expected `Open navigation menu`.
- `destinations`: List of `NavigationMenuItem`.
- `canSignOut`: Whether Sign Out should be displayed.
- `isAuthenticated`: Whether protected content may be shown.

Rules:

- Must not include tokens, emails, provider ids, raw session state, or hidden route parameters.
- Must never expose protected destinations when `isAuthenticated` is false.
- Must close the drawer after a destination or sign-out action.

## NavigationMenuItem

Represents one item in the hamburger navigation menu.

Fields:

- `route`: Destination route when applicable.
- `label`: User-visible text.
- `contentDescription`: Accessibility label.
- `iconResourceName`: Local drawable resource name.
- `selected`: Whether this item is the current destination.
- `requiresAuthentication`: Whether the item is protected.
- `actionType`: `Navigate`, `SignOut`, or `Dismiss`.

Rules:

- Labels must be short enough for compact width and large font.
- Selected state must be visually and semantically available.
- Sign Out is an action, not a route, and must clear authenticated access.

## DarkAuthenticatedThemeTokens

Represents the login-inspired authenticated visual system.

Fields:

- `background`: Deep navy app background.
- `surface`: Elevated blue-gray screen/container surface.
- `surfaceMuted`: Lower-emphasis section surface.
- `accent`: Teal action/selection accent.
- `onBackground`: High-contrast primary text.
- `onSurfaceMuted`: Secondary text.
- `errorContainer`, `warningContainer`, `successContainer`: State surfaces.
- `cornerRadius`: Shared radius matching the existing design system.

Rules:

- Tokens should be centralized and reused instead of copied per screen.
- Dynamic color should not override the premium authenticated surface when it reduces continuity with login.
- Text contrast must remain readable in dark mode, large font, and display zoom.

## AuthenticatedScreenPresentation

Represents the restyled display state for post-login screens.

Fields:

- `route`: Screen route.
- `screenTitle`: Display title.
- `sections`: Content sections/cards/panels.
- `primaryAction`: Main action where applicable.
- `secondaryActions`: Supporting actions.
- `validationMessages`: User-safe form feedback.
- `emptyState`: Optional local empty state.
- `errorState`: Optional local error state.

Rules:

- Must preserve the screen's existing user workflow.
- Must not introduce extra navigation steps for scoring input.
- Must not display raw provider/session/debug data.

## WorkflowProtectionState

Represents temporary workflow state affected by navigation.

Fields:

- `hasUnsavedPlayerDraft`: Player form dirty state.
- `hasUnsavedSeasonDraft`: Season form dirty state.
- `hasUnsavedHandicapEdit`: Handicap edit dirty state.
- `hasPendingScoreEntry`: Scoring input dirty state.
- `safeNavigationMessage`: Optional user-safe warning.

Rules:

- Navigation from the drawer should not silently destroy critical in-progress scoring or form edits.
- If confirmation is needed, copy must be user-safe and not include private details.

## NavigationDiagnosticEvent

Represents privacy-safe shell observability.

Fields:

- `name`: Event name such as `android_nav_menu_opened`, `android_nav_destination_selected`, `android_nav_sign_out_selected`, or `android_nav_protected_redirect`.
- `destination`: Coarse route name, such as `home`, `scoring`, or `settings`.
- `menuState`: `opened` or `closed`.
- `result`: `selected`, `dismissed`, `sign_out`, or `redirected`.
- `timingBucket`: Coarse timing bucket where needed.

Rules:

- Must not include tokens, emails, provider ids, player names, raw scores, session ids, route arguments, or debug logs.
- Must be covered by redaction tests.

## State Transitions

```text
Authenticated route visible
  -> Hamburger selected
  -> Drawer open with selected destination
  -> Destination selected
  -> Drawer closes
  -> Protected route renders

Authenticated route visible
  -> Hamburger selected
  -> Sign Out selected
  -> Drawer closes
  -> Authenticated state cleared
  -> Login

Unauthenticated route request
  -> Protected route checked
  -> Login
```
