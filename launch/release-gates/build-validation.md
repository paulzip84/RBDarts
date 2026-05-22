# Build Candidate Validation

Status: Draft pass for committed launch artifacts; signed archives remain pending.

## Automated Checks

- Launch artifact validator: passed locally, 11 checked and 0 skipped.
- Launch validator test suite: passed locally, 12 tests.
- Firebase launch smoke suite: passed locally with the existing Firebase function tests, 14 tests.
- iOS source type-check: passed locally with launch sources included.
- Android Gradle verification: passed with the checked-in Gradle wrapper, Android Studio JDK 21, `testDebugUnitTest`, and `lintDebug`.
- iOS release configuration unit coverage: `ios/RBDartsTests/Launch/ReleaseConfigurationTests.swift`
- Android release configuration unit coverage: `android/app/src/test/java/com/rbdarts/launch/ReleaseConfigurationTest.kt`
- Firebase production template smoke coverage: `firebase/functions/test/launchReadiness.test.ts`

## Manual Checks Required Before Submission

- Produce a signed iOS archive and upload through App Store Connect.
- Produce a signed Android AAB and upload through Google Play Console.
- Install release candidates from the platform beta channel, not debug builds.
- Record device, OS, app version, build number, and reviewer path evidence.

## Result

Build candidate records are structurally valid and ready for beta-channel validation. Final pass requires signed artifact identifiers and device evidence.
