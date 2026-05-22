# Third-Party Services Inventory

| Service | Purpose | User Data Involved | Launch Disclosure Impact |
|---------|---------|--------------------|--------------------------|
| Firebase Auth | SSO identity broker | Provider id, account id, auth metadata | Authentication and account data |
| Cloud Firestore | League and score storage | Profile, league, score, practice, correction data | App functionality data |
| Cloud Functions | Trusted operations | Request metadata and official scoring inputs | App functionality data |
| Firebase App Check | Abuse protection | Device/app attestation metadata | Security/fraud prevention |
| Firebase Crashlytics | Crash diagnostics | Crash metadata and app state | Diagnostics |
| Firebase Performance Monitoring | Performance diagnostics | Performance traces and device/app metadata | Diagnostics |
| Google Sign-In | SSO provider | Google identity data | Authentication |
| Facebook Login | SSO provider | Facebook identity data | Authentication |
| Apple Sign In | SSO provider | Apple identity relay data | Authentication |

## Launch Rule

Any new service that processes user data returns Apple App Privacy and Google
Data Safety disclosures to `needsRevision`.
