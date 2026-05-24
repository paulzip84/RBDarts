# Contract: Launch Gates

**Feature**: 002-launch-mobile-apps  
**Purpose**: Define the release gate evidence required before beta, store
submission, and public launch.

## Gate Status Values

- `notStarted`: No evidence has been collected.
- `passing`: Evidence satisfies the gate.
- `failed`: Evidence does not satisfy the gate.
- `deferred`: Owner accepts the risk and records follow-up.
- `notApplicable`: Gate does not apply to this launch candidate.

## Mandatory Critical Gates

| Gate | Evidence Required | Blocks |
|------|-------------------|--------|
| Production identity | App id/package id, version, build number, branding, icon, and release configuration reviewed | Beta and submission |
| Secret scan | No committed secrets, signing materials, private Firebase configs, reviewer credentials, or production keys | Beta and submission |
| Auth smoke | Sign-in, sign-out, provider cancellation, provider failure, session expiration, and reviewer access path validated | Submission |
| Core scoring smoke | Standalone scoring, invalid score rejection, completion, and final scorecard validated | Submission |
| Recovery smoke | Active scoring survives restart, backgrounding, network loss, and session interruption | Submission |
| Privacy disclosure | Apple privacy and Google data-safety/app-content answers match the submitted build | Submission |
| Store listing complete | Metadata, screenshots, release notes, support URL, privacy policy URL, and reviewer notes complete | Submission |
| Accessibility review | Primary score entry, auth, practice, corrections, and summaries usable with supported accessibility settings | Public release |
| Crash/data-loss gate | No unresolved critical crash, startup failure, broken auth, score-save failure, or data-loss defect | Public release |
| Monitoring readiness | Crash, sign-in, score-save, backend, support, escalation, hotfix, and rollback monitoring defined | Public release |

## Deferral Rules

- Critical gates may be deferred only by the app owner and only when public
  release is not exposed to users until the risk is removed or accepted in the
  launch readiness report.
- Privacy, committed secret, reviewer access, crash-on-launch, broken sign-in,
  and score data-loss gates cannot be deferred for public release.
- Every deferral requires owner, date, rationale, user impact, mitigation, and
  follow-up.

## Evidence Format

Each gate result must record:

- Gate id and candidate id
- Status
- Owner
- Evidence link or artifact path
- Device/platform coverage where relevant
- Decision notes
- Decision timestamp

## Pass Condition

A launch candidate may enter store submission only when:

- All submission-blocking critical gates are `passing`
- Non-critical failures have owner-approved follow-up
- Store listing and privacy disclosures are `ready`
- Reviewer access is tested and revocable

A launch candidate may enter public release only when:

- Store review approval is recorded
- Launch readiness report is approved
- Monitoring plan is active
- No non-deferrable critical gates are failed or stale
