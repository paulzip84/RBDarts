# iOS Build Identity Checklist

Status: Draft evidence, pending signed archive.

## Required Identity

- Bundle identifier: `com.rbdarts.app`
- Display name: `RBDarts`
- Marketing version: `0.1.0`
- Build number: `1`
- Configuration profile: `production`
- Signing: Apple Distribution certificate and provisioning profile from secure local or CI storage
- Firebase project: `rbdarts-production`
- SSO providers: Google and Facebook production clients

## Gate Checks

- No development or staging label appears in the UI.
- `ReleaseConfig.plist` is supplied from secure build configuration and is not committed.
- `ReleaseConfig.example.plist` remains a template only.
- Archive is built from a clean branch and tagged release candidate.
- App privacy/support/account deletion links are visible from the app shell.

## Evidence To Attach

- Xcode archive identifier
- App Store Connect build number
- Smoke test device and iOS version
- Sign-in and scoring recovery notes
