# Design QA: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## Baseline Delta

Previous state:

- Centered vector mark on a plain Material background.
- App name, version/build, and generic loading status centered vertically.

Redesigned state:

- Full-bleed local artwork derived from `RBDarts_Login`.
- Dark scrim over the lower portion of the image.
- Bottom-safe brand block with `RBDarts`, version/build, progress indicator, and loading status.
- Fallback state preserves the existing local vector mark.

## Visual Checks

- [x] `RBDarts` remains visible over the artwork.
- [x] Version/build remains visible over the artwork.
- [x] Loading status remains visible and is not only represented by a spinner.
- [x] No Google/Facebook login buttons appear on the load screen.
- [x] No password, password reset, provider error, stack trace, or debug text appears.
- [x] Compact layout has a stable bottom status region.
- [x] Expanded layout keeps artwork and status visible.
- [x] Dark and light theme tests keep brand text visible.
- [x] Fallback artwork mode has a local branded state.

## Screenshot Evidence Targets

- Default cold launch load screen on phone.
- Fallback load screen rendered in Compose UI test state.
- Optional tablet/foldable crop review.
- Optional dark/light theme side-by-side review.

## Evidence Paths

- Manual smoke notes: `specs/005-load-screen-redesign/evidence/load-screen-smoke.md`
- Baseline notes: `specs/005-load-screen-redesign/evidence/load-screen-baseline.md`
- Runtime asset: `android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg`

## Pending Manual Review

- Emulator/device screenshot capture is blocked by current emulator instability.
- TalkBack live announcement check remains manual.
- Rotation and display zoom live smoke remain manual.

## Verification Results

- Compose UI source compilation passed for default artwork, fallback, version label, progress, no-login-controls, compact layout, expanded layout, theme contrast, large font, artwork semantics, and lifecycle tests.
- Live emulator installation passed after reboot, but usable screenshot evidence could not be captured because the emulator displayed system ANR dialogs and later disconnected from adb.
