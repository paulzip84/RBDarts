# Research: Mobile App Store Launch Readiness

**Feature**: 002-launch-mobile-apps  
**Date**: 2026-05-22

## Decision: Keep Store Submission Authority In The Store Consoles

Use App Store Connect and Google Play Console as the authoritative systems for
submission status, store review communication, production rollout, app content
forms, and release decisions. Keep repository artifacts as drafts, evidence,
runbooks, and repeatable checklists.

**Rationale**: Apple and Google own final store metadata validation, review
status, release controls, and policy prompts. Repository artifacts make the work
auditable and reviewable before upload, but they cannot replace store-console
state.

**Alternatives considered**:

- Repository-only launch checklist: Rejected because it cannot verify live store
  fields, review status, app access forms, or release availability.
- Store-console-only process: Rejected because it leaves no durable local
  evidence for specs, tasks, review, or future release repeatability.

**Sources**:

- Apple App Store Connect submission help:
  https://developer.apple.com/help/app-store-connect/manage-submissions-to-app-review/submit-for-review/
- Apple App Review Guidelines:
  https://developer.apple.com/app-store/review/guidelines/
- Google Play Console prepare and roll out releases:
  https://support.google.com/googleplay/android-developer/answer/9859348

## Decision: Treat Privacy/Data-Safety Answers As Release-Blocking Contracts

Create launch artifacts that map actual app behavior to Apple App Privacy
details and Google Play Data Safety/App Content answers before submission.
Require re-review when auth providers, diagnostics, backend data fields, support
flows, or third-party components change.

**Rationale**: The app handles identity, league membership, scoring history,
practice data, correction reasons, support requests, diagnostics, and account
deletion requests. Incorrect privacy declarations can delay review, undermine
user trust, and violate the project constitution.

**Alternatives considered**:

- Fill forms at the end from memory: Rejected because privacy answers need
  evidence from the shipped build and dependencies.
- Use generic low-detail privacy language: Rejected because store disclosures
  must match data collection, use, sharing, retention, tracking, and deletion.

**Sources**:

- Apple App privacy details:
  https://developer.apple.com/app-store/app-privacy-details/
- Apple App Store Connect privacy overview:
  https://developer.apple.com/help/app-store-connect/manage-app-privacy/
- Google Play Data safety form:
  https://support.google.com/googleplay/android-developer/answer/10787469
- Google Play App content:
  https://support.google.com/googleplay/android-developer/answer/9859455

## Decision: Use TestFlight And Google Play Testing Tracks Before Public Launch

Validate release candidates through TestFlight and Google Play internal/closed
testing before public rollout. Record tester coverage, device coverage, critical
journey results, crash-free sessions, known issues, and release-gate decisions.

**Rationale**: Store launch requires device diversity and operational evidence
that local tests cannot provide. Beta channels also exercise realistic install,
auth, backend, recovery, and diagnostics flows without public release risk.

**Alternatives considered**:

- Local simulator/emulator-only testing: Rejected because it misses real device,
  identity provider, notification, network, and store-distribution behavior.
- Public release without beta: Rejected because crash/data-loss issues in live
  scoring would be high trust-impact failures.

**Sources**:

- Apple TestFlight overview:
  https://developer.apple.com/testflight/
- Google Play testing overview:
  https://support.google.com/googleplay/android-developer/answer/9845334

## Decision: Provide Revocable Reviewer Access

Support store review through revocable test accounts or a review-safe demo path
that reaches authenticated MVP flows without exposing personal credentials.
Document provider expectations, test data, and recovery instructions in the
reviewer access plan.

**Rationale**: RBDarts uses SSO and protected league/profile data. Reviewers
must reach the app's core experience even if personal provider sign-in is
unsuitable, unavailable, or blocked by provider setup. Credentials and test
accounts must be revocable and excluded from the repository.

**Alternatives considered**:

- Require reviewers to use personal SSO accounts: Rejected because it may block
  review and can create privacy/support risk.
- Commit shared review credentials: Rejected because credentials are secrets and
  the constitution forbids committing secrets.

**Sources**:

- Apple app review information and sign-in guidance:
  https://developer.apple.com/help/app-store-connect/reference/app-review-information
- Google Play App Content and App Access:
  https://support.google.com/googleplay/android-developer/answer/9859455

## Decision: Enforce Current Platform Target And Policy Checks Before Final Submission

Add a final policy refresh gate that verifies current Apple review guidance,
current Google Play target API level requirements, app content answers, account
deletion expectations, and any required developer account setup before final
submission.

**Rationale**: Store rules and target-level requirements change. A plan written
on 2026-05-22 must require final re-check near submission so a stale target API,
policy disclosure, app access issue, or account setup gap does not block launch.

**Alternatives considered**:

- Freeze requirements from planning date: Rejected because launch compliance is
  temporal and store requirements are externally controlled.
- Defer policy check until after rejection: Rejected because it wastes review
  cycles and delays launch.

**Sources**:

- Google Play target API level requirements:
  https://support.google.com/googleplay/android-developer/answer/11926878
- Apple App Review Guidelines:
  https://developer.apple.com/app-store/review/guidelines/

## Decision: Gate Public Release With Operational Monitoring And Hotfix Criteria

Define launch monitoring thresholds for crashes, sign-in failures, score-save
failures, backend errors, support volume, and store-review issues. Require a
named support owner, escalation path, and hotfix/rollback criteria before public
rollout.

**Rationale**: App approval is not enough for a safe public launch. RBDarts must
protect live scorekeeping from early production failures and have a rapid
response path for severe issues.

**Alternatives considered**:

- Manual ad hoc monitoring after release: Rejected because severe sign-in or
  score-save issues need defined triage timing and ownership.
- Delay monitoring until usage grows: Rejected because launch day is when
  configuration and review-environment mismatches are most likely.

## Decision: Launch Artifacts Are Non-Secret Repository Files

Add a top-level `launch/` structure for non-secret launch artifacts: store
metadata drafts, screenshot checklists, privacy/data-safety answers, beta
evidence, release gates, and runbooks. Keep private signing materials, store
credentials, app-specific passwords, API keys, and reviewer credentials outside
git.

**Rationale**: Launch readiness needs durable evidence and repeatable checklists,
but secret material must not enter source control.

**Alternatives considered**:

- Store all launch materials outside the repository: Rejected because Speckit
  tasks and reviews need versioned evidence.
- Store all launch materials in git: Rejected because signing and review secrets
  must remain outside the repository.
