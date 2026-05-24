# Research: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Sources Reviewed

- Android Developers, Navigation drawer in Compose: https://developer.android.com/develop/ui/compose/components/drawer
- Android Developers, Navigation with Compose: https://developer.android.com/develop/ui/compose/navigation
- Android Developers, Material 3 insets behavior: https://developer.android.com/develop/ui/compose/system/material-insets
- Android Developers, Material 3 adaptive navigation suite API reference: https://developer.android.com/reference/kotlin/androidx/compose/material3/adaptive/navigationsuite/NavigationSuiteDefaults

## Decision 1: Use Material 3 Modal Navigation Drawer For The Hamburger Pattern

**Decision**: Implement the requested top-left hamburger menu with Compose Material 3 `ModalNavigationDrawer`, `ModalDrawerSheet`, and `NavigationDrawerItem`.

**Rationale**: Android's Compose drawer guidance describes a navigation drawer as a slide-in menu for moving across app sections and notes activation by side swipe or menu icon. RBDarts has multiple protected sections and settings, which matches the documented use cases for content organization, account management, and feature discovery.

**Alternatives Considered**:

- Keep the current bottom navigation: rejected because the user explicitly requested replacing it.
- Keep the wide-screen navigation rail: rejected because it would retain a second navigation paradigm after the requested hamburger shift.
- Add a custom animated menu: rejected because Material 3 provides tested drawer semantics, state, insets, and selected-item behavior.

## Decision 2: Keep Top-Left Hamburger Across Compact And Expanded Layouts

**Decision**: Use a top app bar `navigationIcon` hamburger on authenticated screens for compact phones, large phones, tablets, and foldables.

**Rationale**: The requirement specifically asks for the hamburger button at the top left. Using a consistent affordance across sizes avoids switching back to bottom navigation or rail on larger screens and keeps the redesign predictable.

**Alternatives Considered**:

- Adaptive `NavigationSuiteScaffold`: rejected for 006 because it can select bottom bar, rail, or drawer patterns and would conflict with the explicit "no bottom navigation" request unless heavily constrained.
- Permanent drawer on expanded width: deferred. It may be useful later, but this feature asks for a hamburger button pattern, so a modal/dismissible drawer is the safer first implementation.

## Decision 3: Preserve Existing Route Enum And Manual Navigation State

**Decision**: Continue using `RBDartsRoute`, `RBDartsDestinations`, `RBDartsAppRoot`, and existing state callbacks instead of introducing Navigation Compose.

**Rationale**: Android's Compose navigation guidance recommends a navigation graph/controller when adopting the Navigation component. RBDarts already has a small route enum and app shell; replacing navigation infrastructure during a visual shell redesign would expand risk without being necessary for the requested UX.

**Alternatives Considered**:

- Introduce `navigation-compose`: rejected for this feature because it is not needed to deliver the hamburger shell and would add migration work across every route.
- Per-screen local navigation state: rejected because protected route enforcement should remain centralized.

## Decision 4: Extend The Login-Inspired Dark Visual System

**Decision**: Promote the login page's dark palette direction into reusable authenticated app tokens: deep navy background, elevated blue-gray surfaces, teal accent, high-contrast white text, muted secondary text, and restrained error/warning/success containers.

**Rationale**: The user asked for the rest of the app to match the login page's dark mode theme, colors, and style. Central tokens reduce drift and make tests/QA easier.

**Alternatives Considered**:

- Use current Material dynamic color defaults: rejected for authenticated screens because it can weaken continuity with the custom login styling.
- Copy login composables directly into every screen: rejected because it creates duplication and inconsistent maintenance.

## Decision 5: Use Material Components For Insets And Accessibility

**Decision**: Use Material 3 `Scaffold`, top app bar, drawer sheet, and drawer item components so system insets, touch targets, selected states, and accessibility semantics come from native Compose Material components where possible.

**Rationale**: Android's Material 3 inset documentation notes that many Material 3 components handle relevant system insets automatically, including top app bars and drawer sheets. This helps avoid clipped hamburger/menu content on gesture navigation devices.

**Alternatives Considered**:

- Manual full-screen layout with custom padding: rejected because it is more likely to miss status/navigation bar insets and accessibility behavior.

## Decision 6: Add Local Vector Icons Instead Of A New Icon Dependency

**Decision**: Use local vector drawables for hamburger, sections, and sign-out if icons are needed.

**Rationale**: The current app does not include a Material Icons dependency. Local vector drawables keep the dependency surface stable and satisfy the need for recognizable icon buttons.

**Alternatives Considered**:

- Add `material-icons-extended`: deferred because it increases dependency surface for a small icon set.
- Use text-only menu items and hamburger: rejected because icon buttons and destination icons improve scanability and accessibility.

## Decision 7: Verify Scoring And Forms As First-Class Workflow Surfaces

**Decision**: Treat scoring, player creation, season creation, and handicap management as workflow-critical surfaces that require dark-theme, large-font, and action visibility tests.

**Rationale**: The authenticated shell redesign touches live play and setup screens. Dark styling and drawer layout cannot obscure primary actions or introduce scoring delays.

**Alternatives Considered**:

- Test only the shell/navigation container: rejected because screen-level regressions are likely when restyling forms and scoring controls.
