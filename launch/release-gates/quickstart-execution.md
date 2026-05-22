# Quickstart Execution Notes

Status: Draft pass for local non-device checks.

## Commands

```bash
node --test scripts/launch/validate-launch-artifacts.test.mjs
node scripts/launch/validate-launch-artifacts.mjs
node scripts/launch/check-public-urls.mjs launch/privacy/public-url-verification.md launch/app-store/metadata/en-US.json launch/google-play/metadata/en-US.json
node --test --experimental-strip-types firebase/functions/test/*.test.ts
```

## Manual Steps

- Run iOS unit and UI tests in Xcode.
- Run Android unit and instrumentation tests in Android Studio or Gradle.
- Upload signed release candidates to beta tracks.
- Attach signed build identifiers to release gate evidence.

## Result

Local Node launch validation, Firebase function tests, plist lint, JSON parse checks, Swift source type-check, Android unit tests, and Android lint passed. iOS `xcodebuild` verification still requires full Xcode; this machine currently has Command Line Tools selected and no `/Applications/Xcode.app` was found. The official Mac App Store Xcode page was opened for interactive installation. Device validation remains pending.
