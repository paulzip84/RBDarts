# Quickstart: Baseball Darts Rules

## Prerequisites

- Android Gradle wrapper exists at `android/gradlew`.
- Xcode command-line tools or Xcode are available for iOS verification.
- No secrets, tokens, or signing materials are required for local rule tests.

## Android Verification

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest compileDebugAndroidTestKotlin lintDebug assembleDebug
```

Verified on 2026-05-24: `testDebugUnitTest`, `compileDebugAndroidTestKotlin`,
`lintDebug`, and `assembleDebug` passed.

Expected coverage:

- Standard 9-inning individual scoring.
- Dart-level single/double/triple/miss scoring.
- Team score aggregation.
- Extra innings and equal-turn completion.
- Post-20 tiebreaker targets.
- Corrections, locks, role authorization, and audit records.
- Scoreboard and analytics derived state.

## iOS Verification

```bash
xcodebuild test -project ios/RBDarts.xcodeproj -scheme RBDarts -destination 'platform=iOS Simulator,name=iPhone 16'
```

If the named simulator is unavailable, use an installed iOS 17+ simulator destination.

Current local blocker: `xcodebuild` requires full Xcode, but this machine's
active developer directory is Command Line Tools. Swift domain source files were
type-checked with `xcrun swiftc` and a temporary module cache.

Expected coverage:

- Swift scoring rules match Kotlin fixtures.
- Score correction validation and audit creation.
- Locked-game behavior.
- Extra-inning target resolution.
- Derived scoreboard and analytics values.

## Shared Contract Verification

Add or update fixtures under:

```text
shared-contracts/schemas/
shared-contracts/fixtures/scoring/
shared-contracts/fixtures/corrections/
shared-contracts/fixtures/locking/
```

Each fixture should include enough expected values for Android and iOS tests to assert the same totals, winner state, margin, target number, and lock/correction outcome.

## Manual Smoke Scenarios

1. Start a standard individual Baseball Darts game.
2. Enter three valid darts in the 1st inning and verify target `1`, player inning score, total, leader, and margin.
3. Enter a miss/wrong-number/bounce-out and verify it scores `0`.
4. Complete nine innings where one participant wins and verify no extra inning is requested.
5. Complete nine tied innings and verify the 10th inning targets `10`.
6. In extra innings, let the first participant lead before the second participant throws and verify no winner is declared yet.
7. Complete the extra inning and verify the winner appears only after equal turns.
8. Run a team game and verify team inning totals equal the sum of player turns.
9. Apply an authorized score correction and verify audit history, totals, winner, standings, and analytics update.
10. Lock a completed game and verify unauthorized users cannot edit scores.

## Diagnostics Review

Confirm diagnostics emit privacy-safe event names and reason codes only. Do not log tokens, email addresses, raw provider payloads, free-form correction reasons, or secrets.
