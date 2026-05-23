# Contract: Android UI State And Recovery

**Feature**: `003-android-material-you-ui`

## State Principles

- Each primary screen exposes one immutable UI state model and explicit user events.
- UI state must be derived from stable app/domain models where possible.
- Draft form data must survive configuration changes.
- In-progress scoring must survive background/resume and process recreation using existing recovery mechanisms or a documented replacement.
- UI errors must be privacy-safe and user-actionable.

## Required States Per Screen

| Screen | Required States |
| --- | --- |
| Loading | Loading, routed unauthenticated, routed authenticated, startup error |
| Login | Idle, provider selected, signing in, cancelled, failed, offline, authenticated |
| Game Type | Loading options, options available, invalid selection, selected |
| Player Creation | Empty, editing, validation error, saving, saved, save failed |
| Season Creation | Empty, editing, validation error, saving, saved, save failed |
| Handicap Management | Loading, view only, editing, validation error, saving, saved, audit unavailable |
| Scoring | Setup incomplete, active entry, invalid entry, saving, saved, offline queued, recovered, completed, locked |
| Settings | Ready, signing out, sign-out failed |

## Accessibility Contract

- Every interactive control has a clear label.
- Minimum touch target size is respected for primary buttons, navigation items, score entry controls, and provider actions.
- Focus order follows visual and workflow order.
- Large font and display-size settings do not hide score entry, totals, or primary navigation.
- Color is not the only indicator of score validity, selected state, or error state.

## Diagnostics Contract

- Diagnostics may record route names, provider category, error category, and performance timings.
- Diagnostics must not record SSO tokens, raw provider responses, user contact details, private player notes, correction rationale, or secrets.
