# Firebase Production Readiness

Status: Draft.

## Required Controls

- Production Firebase project: `rbdarts-production`
- App Check enforcement: enabled
- Firestore rules: default deny with explicit trusted operations
- Cloud Functions: deployed from reviewed source
- Crashlytics and Performance: production project only
- Production config files: supplied by local/CI secure storage and not committed

## Automated Coverage

- `firebase/functions/test/launchReadiness.test.ts`
- Existing Firestore and domain tests under `firebase/functions/test/`

## Result

Template configuration is ready. Final pass requires Firebase console verification and deployed production services.
