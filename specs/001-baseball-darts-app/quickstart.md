# Quickstart: Baseball Darts Mobile App

**Feature**: 001-baseball-darts-app  
**Date**: 2026-05-22

This quickstart validates the planned product behavior and the implementation
scaffold now present in `ios/`, `android/`, `firebase/`, and
`shared-contracts/`.

## 1. Environment Expectations

- Xcode with current iOS SDK.
- Android Studio with current Android SDK.
- Firebase project with Auth, Firestore, Cloud Functions, App Check,
  Crashlytics, and Performance Monitoring configured.
- Google, Facebook, and Apple identity providers configured for development.
- Firebase emulator suite available for local auth/rules/functions tests.

## 2. Configuration Checklist

Before running app scenarios:

- iOS app has bundle identifier registered with Firebase and Apple developer
  settings.
- Android app has application id, signing certificate hashes, and OAuth client
  configured.
- Google provider is enabled.
- Facebook provider is enabled and uses official SDK setup for each platform.
- Apple provider is enabled for iOS.
- Firestore rules default to deny and include role-aware access tests.
- App Check is configured for development and production modes.
- No secrets, provider keys, signing material, or tokens are committed.

## 3. Contract Fixture Validation

Both apps and backend validation must pass shared fixtures from
`shared-contracts/fixtures/`.

Required fixture groups:

- `scoring`: valid scores, invalid scores, totals, extra innings.
- `handicap`: equal averages, lower-average handicap, rounding modes.
- `locking`: Game N locks when Game N+1 starts.
- `corrections`: audit record and downstream recalculation.
- `standings`: ranking order and match points.
- `practice`: practice isolation from official stats.

Pass condition:

- iOS, Android, and backend calculation tests produce identical expected outputs.

Current local backend validation:

```bash
node --test --experimental-strip-types firebase/functions/test/*.test.ts
```

Current local Swift source type check:

```bash
CLANG_MODULE_CACHE_PATH=/private/tmp/rbdarts-clang-cache \
SWIFT_MODULE_CACHE_PATH=/private/tmp/rbdarts-swift-cache \
swiftc -typecheck ios/RBDarts/Shared/Domain/*.swift \
  ios/RBDarts/Shared/Data/*.swift \
  ios/RBDarts/Shared/Security/*.swift \
  ios/RBDarts/Shared/Observability/*.swift \
  ios/RBDarts/Shared/DesignSystem/*.swift \
  ios/RBDarts/Features/**/*.swift \
  ios/RBDarts/App/RBDartsApp.swift
```

Full native build commands after installing local toolchains:

```bash
# iOS, from repository root once full Xcode is selected
xcodebuild test -project ios/RBDarts.xcodeproj -scheme RBDarts -destination 'platform=iOS Simulator,name=iPhone 16'

# Android, from android/ once Gradle wrapper or local Gradle is available
./gradlew testDebugUnitTest connectedDebugAndroidTest

# Firebase, from firebase/functions after dependency installation
npm install
npm test
```

## 4. Manual MVP Scenario: Standalone Game

1. Sign in with a trusted provider.
2. Create a standalone game with two players.
3. Enter inning scores from 0 through 9.
4. Attempt an invalid score such as 10 and verify it is rejected.
5. Finish a tied regulation game and continue into extra innings.
6. Complete the game and verify final scorecard, winner, margin, zero innings,
   and 9-point innings.

Pass condition:

- Scores are preserved, totals are correct, and final summary matches the
  scorecard.

## 5. Manual MVP Scenario: League Match

1. Create a league.
2. Create two teams.
3. Add players and seed averages.
4. Configure games per match, players per team per game, handicap percent, and
   handicap rounding.
5. Create a match.
6. Select lineups for Game 1.
7. Score Game 1 and complete it.
8. Start Game 2 and verify Game 1 locks.
9. Complete all games.
10. Finalize the match.

Pass condition:

- Handicap, adjusted totals, game points, match bonus, standings, and player
  averages match expected calculations.

## 6. Manual MVP Scenario: Locked Correction

1. Open a locked game from a finalized or in-progress match.
2. Submit a correction request with a required reason.
3. Approve/apply the correction as a League Manager.
4. Review the audit record.
5. Review recalculated game summary, match summary, standings, and player stats.

Pass condition:

- Regular users cannot edit the locked score directly, the audit record is
  complete, and every derived official value is recalculated.

## 7. Manual MVP Scenario: Practice Isolation

1. Select a player.
2. Select a target.
3. Enter multiple practice scores.
4. Review practice statistics.
5. Review official league averages and standings.

Pass condition:

- Practice stats update, but official league stats do not change.

## 8. Recovery and Stability Scenarios

Run these on both platforms:

- Background the app during live score entry and resume.
- Force quit during live score entry and reopen.
- Disable network during score entry, continue entering scores, then reconnect.
- Expire or revoke auth session and verify recovery.
- Cancel SSO provider flow.

Pass condition:

- No entered scores silently disappear, the user sees a clear recovery path, and
  official records do not become half-locked or half-finalized.

## 9. Privacy and Observability Review

Before release:

- Review crash logs, analytics events, performance traces, and error reports.
- Confirm no tokens, secrets, private provider identifiers, unredacted correction
  reasons, or unnecessary personal data are present.
- Confirm export scope is visible before export creation.

Pass condition:

- Privacy review finds no sensitive data leakage.
