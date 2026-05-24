# Quickstart: Mobile App Store Launch Readiness

**Feature**: 002-launch-mobile-apps  
**Date**: 2026-05-22

This quickstart describes the launch-readiness workflow for the native RBDarts
iOS and Android apps. It assumes the MVP app implementation exists and that
store-account owners can access App Store Connect, Google Play Console,
Firebase, identity provider settings, support channels, and the privacy policy
domain.

## 1. Confirm Launch Scope

1. Confirm this release targets the MVP scope only.
2. Confirm no paid goods, subscriptions, ads, gambling positioning,
   children-directed positioning, or new locales are included.
3. Confirm supported device and OS targets.
4. Confirm privacy policy, support, account deletion request, and terms links.

Pass condition:

- Scope and external dependencies are recorded before release-candidate work.

## 2. Prepare Non-Secret Launch Artifact Workspace

The implementation keeps non-secret launch artifacts under `launch/` and
validation scripts under `scripts/launch/`. The artifact index is:

- `launch/INDEX.md`
- `launch/README.md`
- `launch/release-gates/gate-catalog.json`
- `launch/release-gates/launch-readiness-report.md`
- `scripts/launch/validate-launch-artifacts.mjs`
- `scripts/launch/check-public-urls.mjs`

Create missing directories if setting up from a clean checkout:

```bash
mkdir -p launch/app-store/metadata
mkdir -p launch/app-store/screenshots
mkdir -p launch/app-store/review-notes
mkdir -p launch/google-play/metadata
mkdir -p launch/google-play/screenshots
mkdir -p launch/google-play/app-content
mkdir -p launch/privacy
mkdir -p launch/beta
mkdir -p launch/release-gates
mkdir -p launch/runbooks
```

Do not place signing keys, provisioning profiles, keystores, production Firebase
configs, reviewer passwords, app-specific passwords, or store credentials in
these directories.

## 3. Build Release Candidates

1. Configure production app identity for iOS and Android.
2. Configure release signing through secure local or CI secrets.
3. Use production Firebase and SSO provider configuration.
4. Build installable release candidates for both platforms.
5. Record candidate id, platform, version, build number, configuration profile,
   and artifact reference.

Evidence paths:

- `launch/release-gates/ios-build-identity.md`
- `launch/release-gates/android-build-identity.md`
- `launch/release-gates/ios-launch-candidate.json`
- `launch/release-gates/android-launch-candidate.json`

Pass condition:

- Release candidates install on supported devices and contain no visible
  development labels, debug-only paths, placeholder screenshots, or sample
  secrets.

## 4. Run Smoke And Recovery Gates

Run on representative iOS and Android devices:

1. Launch app.
2. Sign in.
3. Create standalone game.
4. Enter valid and invalid scores.
5. Background and resume during score entry.
6. Restart during score entry.
7. Disable network during score entry and reconnect.
8. Complete game and view final scorecard.
9. Enter practice scores.
10. View summary/projection.
11. Sign out.
12. Open privacy policy, support, and account deletion request paths.

Pass condition:

- No critical crash, data loss, broken sign-in, blocked reviewer path, or
  production configuration issue remains.

Local validation commands:

```bash
node --test scripts/launch/validate-launch-artifacts.test.mjs
node scripts/launch/validate-launch-artifacts.mjs
node --test --experimental-strip-types firebase/functions/test/*.test.ts
```

Android source, unit, and lint verification:

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest lintDebug
```

iOS source type-check command:

```bash
CLANG_MODULE_CACHE_PATH=/private/tmp/rbdarts-clang-cache \
SWIFT_MODULE_CACHE_PATH=/private/tmp/rbdarts-swift-cache \
swiftc -typecheck ios/RBDarts/Shared/Domain/*.swift \
  ios/RBDarts/Shared/Data/*.swift \
  ios/RBDarts/Shared/Security/*.swift \
  ios/RBDarts/Shared/Observability/*.swift \
  ios/RBDarts/Shared/DesignSystem/*.swift \
  ios/RBDarts/Shared/Launch/*.swift \
  ios/RBDarts/Features/Auth/*.swift \
  ios/RBDarts/Features/StandaloneGame/*.swift \
  ios/RBDarts/Features/League/*.swift \
  ios/RBDarts/Features/MatchScoring/*.swift \
  ios/RBDarts/Features/Corrections/*.swift \
  ios/RBDarts/Features/Practice/*.swift \
  ios/RBDarts/Features/Stats/*.swift \
  ios/RBDarts/Features/Settings/*.swift \
  ios/RBDarts/App/RBDartsApp.swift
```

Full iOS project verification after installing Xcode:

```bash
sudo xcode-select -s /Applications/Xcode.app/Contents/Developer
sudo xcodebuild -runFirstLaunch
IOS_DESTINATION="platform=iOS Simulator,name=iPhone 16" \
  scripts/ios/verify-xcode.sh
```

## 5. Prepare Store Packages

For each store:

1. Draft app name, subtitle or short description, full description, category,
   search metadata, release notes, support URL, privacy policy URL, and review
   notes.
2. Capture screenshots from the release candidate on required device classes.
3. Complete age/content rating answers.
4. Complete Apple App Privacy and Google Data Safety/App Content answers.
5. Prepare reviewer access instructions with revocable test accounts or a
   review-safe demo path.
6. Verify public URLs are reachable without sign-in.

Pass condition:

- Store package has zero missing required fields and matches the installed app.

Evidence paths:

- `launch/app-store/metadata/en-US.json`
- `launch/google-play/metadata/en-US.json`
- `launch/privacy/apple-app-privacy.json`
- `launch/privacy/google-data-safety.json`
- `launch/privacy/public-url-verification.md`

## 6. Run Beta

1. Distribute iOS candidate through TestFlight.
2. Distribute Android candidate through Google Play internal or closed testing.
3. Record tester count, devices, journey coverage, feedback, diagnostics, and
   known issues.
4. Fix or defer issues according to gate severity.
5. Record crash-free evidence or owner-approved alternate evidence if volume is
   too small.

Pass condition:

- Beta cycle is complete and release gates are updated with evidence.

## 7. Approve Launch Readiness

1. Review launch gates.
2. Review privacy disclosure and third-party data inventory.
3. Review accessibility evidence.
4. Review backend, identity provider, and support-channel availability.
5. Review monitoring thresholds and escalation plan.
6. Approve or hold the launch readiness report.

Pass condition:

- Public launch is approved only when non-deferrable critical gates pass.

## 8. Submit And Monitor

1. Submit iOS app in App Store Connect.
2. Submit Android app in Google Play Console.
3. Track review status and reviewer messages.
4. Assign owner and corrective action within one business day for any rejection
   or reviewer question.
5. Release or stage rollout after approval.
6. Monitor crashes, sign-in failures, score-save failures, backend errors, and
   support volume during the first release window.

Pass condition:

- Severe production signals are triaged within one business day and hotfix or
  rollback criteria are followed.

Monitoring and response paths:

- `launch/runbooks/launch-monitoring-plan.json`
- `launch/runbooks/support-readiness.md`
- `launch/runbooks/severe-issue-escalation.md`
- `launch/runbooks/hotfix-rollback.md`
- `launch/release-gates/public-launch-validation.md`
