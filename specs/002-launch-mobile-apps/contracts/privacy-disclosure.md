# Contract: Privacy Disclosure Review

**Feature**: 002-launch-mobile-apps  
**Purpose**: Define the evidence required to confirm store privacy disclosures
match the launched app.

## Data Inventory Scope

The disclosure review must cover:

- Account identity and linked SSO provider data
- Player profile and display name data
- League, team, role, match, game, and score history data
- Practice attempts and practice statistics
- Correction requests, reasons, and audit records
- Support requests and account deletion requests
- Crash, error, performance, and operational diagnostics
- Reviewer/beta access data
- Third-party services used for auth, storage, diagnostics, support, analytics,
  or app improvement

## Required Answers

For each data category, record:

- Whether data is collected
- Whether data is linked to the user
- Whether data is shared with third parties
- Whether data is used for tracking or cross-app purposes
- Purpose of collection
- Retention or deletion expectation
- User-visible choice or deletion path
- Whether collection is required or optional
- Which platform disclosure answer it maps to

## Staleness Rule

Privacy disclosure status becomes `needsRevision` when any of these change:

- Authentication provider behavior
- Diagnostic, crash, performance, analytics, or support event schema
- Firestore or backend data model affecting user data
- Account deletion or privacy support path
- Third-party component that collects or processes user data
- Store policy or form requirement relevant to the app

## Pass Condition

Privacy disclosure is `ready` only when:

- Apple and Google answers are complete.
- App behavior, backend data flows, and third-party services are reviewed.
- Support and account deletion paths are reachable.
- No sensitive data appears in diagnostics or launch evidence.
- The release owner signs off on the final disclosure.
