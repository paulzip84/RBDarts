# Contract: Load Screen UI

Feature: `005-load-screen-redesign`

## Default Visible Content

The load screen must show:

- Full-bleed or immersive RBDarts artwork derived from `RBDarts_Login`.
- App name: `RBDarts`.
- Version/build label.
- User-safe loading message.
- Progress indicator or equivalent loading affordance.

The load screen must not show:

- Raw provider errors.
- Debug build internals beyond approved version/build label.
- Stack traces.
- Tokens, emails, account ids, provider payloads, or session identifiers.
- Login provider buttons or password recovery controls.

## Artwork Rendering

Required behavior:

- Runtime artwork is a local packaged resource.
- No remote image fetch is permitted.
- Artwork preserves recognizable dartboard, red dart flights, and white mascot/mark where practical.
- Text is placed over a scrim, gradient, or safe region.
- Compact and expanded screens keep app text and loading status readable.

Fallback behavior:

- If the image resource is missing, disabled, or cannot render, use a local branded fallback.
- Fallback still shows app name, version/build, and loading status.
- Fallback must not crash or leave a blank screen.

## Accessibility

- Screen-level label: `RBDarts loading screen`.
- App name text must be accessible.
- Version/build text must be accessible.
- Loading status must be accessible.
- Decorative artwork may be hidden from assistive technologies or described concisely as `RBDarts darts artwork`.
- Progress indicator must not be the only accessible status.

## Adaptive Layout

Compact phone:

- App name, version/build, and loading message remain visible without horizontal scrolling.
- Important artwork region remains recognizable.

Large phone/tablet/foldable:

- Artwork may crop differently, but important subject matter remains intentional.
- Text safe region should avoid looking stranded or too small.

Theme and font:

- Dark/light theme and dynamic color must not reduce text contrast below readable levels.
- Large font/display zoom must not overlap the loading message or version label.
