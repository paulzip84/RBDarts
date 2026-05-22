# Contract: Store Submission Package

**Feature**: 002-launch-mobile-apps  
**Purpose**: Define the minimum non-secret package required for Apple App Store
and Google Play submission.

## Shared Package Requirements

Each platform package must include:

- App name and public subtitle/short description
- Full description
- Primary category
- Search metadata appropriate to the store
- App icon status
- Screenshot set and device coverage
- Release notes matching the submitted build
- Support URL
- Privacy policy URL
- Reviewer notes and access instructions
- Privacy/data-safety disclosure status
- Age rating or content rating status
- Production app identity and build number
- Known limitations, if any, that reviewers need to understand

## Apple App Store Package

Required launch evidence:

- App Store Connect app record is available to release owner.
- Bundle id and app version match the submitted iOS build.
- App Review information is complete.
- Sign-in/reviewer access instructions are complete when authenticated features
  are present.
- App Privacy details are reviewed and match app behavior.
- Screenshots and metadata match the iOS/iPadOS experience being submitted.
- Support URL and privacy policy URL are reachable without sign-in.

## Google Play Package

Required launch evidence:

- Play Console app record is available to release owner.
- Application id, version name, and version code match the submitted Android
  build.
- App access instructions are complete when authenticated features are present.
- Data Safety and App Content sections are reviewed and match app behavior.
- Target API level and device compatibility requirements are checked before
  final submission.
- Screenshots and metadata match the Android experience being submitted.
- Support URL and privacy policy URL are reachable without sign-in.

## Rejection Handling Contract

Every store review issue must be recorded with:

- Platform
- Candidate id
- Store message summary
- Issue category
- Severity
- Owner
- Corrective action
- Response due date
- Resolution evidence

Owner and corrective action must be assigned within one business day.
