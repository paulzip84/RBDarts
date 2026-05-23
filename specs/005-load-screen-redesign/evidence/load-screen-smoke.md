# Load Screen Smoke Evidence

Feature: `005-load-screen-redesign`

## Automated Smoke

- Unit tests: Passed.
- Compose UI source compilation: Passed.
- Lint: Passed.
- Debug APK: Passed.

## Manual Smoke Checklist

- [ ] Cold launch shows full-bleed RBDarts artwork on a live emulator/device.
- [x] `RBDarts`, version/build, and loading message are covered by Compose UI tests.
- [x] Load screen routes to login when unauthenticated in route unit tests.
- [x] Authenticated route reaches home or safe preserved route in route unit tests.
- [ ] Rotation keeps status placement stable on a live emulator/device.
- [x] Dark theme keeps contrast in Compose UI tests.
- [x] Light theme keeps contrast in Compose UI tests.
- [x] Large font/display zoom risk is covered by Compose UI source tests.
- [x] Fallback state renders local vector mark in Compose UI tests.
- [ ] TalkBack announces screen, version/build, and loading status on a live emulator/device.

## Screenshot Evidence

Blocked. The debug APK installed and launched on `Medium_Phone_API_36.1`, but screenshot capture did not produce valid RBDarts evidence because the emulator displayed system ANR dialogs, then disconnected from adb. Retry this item on a stable emulator/device.
