# Security Threat Model: RBDarts

## Scope

RBDarts stores identity-backed league records, live score state, practice scores,
correction reasons, audit records, standings, and derived statistics for native
iOS and Android apps backed by Firebase.

## Data Classification

| Data | Classification | Storage Boundary |
|------|----------------|------------------|
| Firebase UID, provider ids, session metadata | Sensitive | Firebase Auth and platform secure storage |
| League roles and permissions | Sensitive authorization data | Firestore and trusted Functions |
| Live score entries | League-confidential until shared by role | Local active-session store and Firestore |
| Locked/finalized scores, standings, averages | Official league records | Firestore, derived by Functions |
| Correction reasons and audit records | Sensitive official audit data | Firestore, role-restricted |
| Practice attempts | Personal performance data | User-owned Firestore records |
| Crash/performance diagnostics | Operational metadata | Privacy-filtered diagnostics only |

## Primary Threats

- Unauthorized users reading private league records.
- Users escalating their own roles or granting themselves League Manager access.
- Clients writing derived official values such as standings, averages, adjusted
  totals, or finalized match points.
- Regular scorekeepers editing locked or finalized game scores without audit.
- SSO token, provider id, email, correction reason, or secret leakage through
  logs, analytics, crash reports, fixtures, or repository files.
- Network loss or app termination causing silent score loss.
- Divergent iOS, Android, and backend scoring calculations.
- Replay or duplicate requests changing official records twice.

## Controls

- No first-party passwords are collected. Authentication is delegated to trusted
  SSO providers through Firebase Auth.
- Firestore rules default deny and expose league data only by role membership.
- Cloud Functions are the trusted boundary for finalization, correction,
  recalculation, and official aggregate writes.
- Mobile clients maintain local active-session recovery state before
  sync-sensitive score transitions.
- Shared JSON fixtures drive parity tests for iOS, Android, and backend scoring.
- Diagnostics wrappers redact token, secret, password, email, provider id, and
  correction reason fields.
- Secret/config templates are committed, while real Firebase files, keystores,
  provisioning profiles, and `.env` files are ignored.

## Residual Risks

- Full Firestore emulator rules tests require Firebase tools and dependency
  installation.
- iOS and Android SSO provider flows require real provider configuration and
  device/simulator testing.
- App Check enforcement must be validated against a real Firebase project before
  production release.
