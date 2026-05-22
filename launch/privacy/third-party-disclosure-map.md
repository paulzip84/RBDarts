# Third-Party Disclosure Map

Status: Draft.

| Service | App Use | Store Disclosure Impact | Launch Evidence |
| --- | --- | --- | --- |
| Firebase Auth | SSO session and identity linkage | User ID collected and linked | `launch/privacy/data-inventory.md` |
| Firestore | User-owned scoring and league records | Gameplay/app activity collected and linked | `launch/privacy/data-inventory.md` |
| Firebase Functions | Trusted scoring operations | Gameplay/app activity processing | `firebase/functions/test/launchReadiness.test.ts` |
| Firebase Crashlytics | Crash diagnostics | Diagnostics collected, not linked where feasible | `launch/privacy/diagnostics-review.md` |
| Firebase Performance | Performance diagnostics | Diagnostics collected, not linked where feasible | `launch/privacy/diagnostics-review.md` |
| Google Sign-In | SSO provider | User ID for authentication | `launch/release-gates/sso-production-readiness.md` |
| Facebook Login | SSO provider | User ID for authentication | `launch/release-gates/sso-production-readiness.md` |

Review this map before store submission and update privacy disclosures if app behavior changes.
