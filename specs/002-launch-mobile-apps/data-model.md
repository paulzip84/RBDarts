# Data Model: Mobile App Store Launch Readiness

**Feature**: 002-launch-mobile-apps  
**Date**: 2026-05-22

## Modeling Principles

- Launch evidence is versioned when it is non-secret and useful for review.
- Store-console state remains authoritative for final submission and rollout.
- Signing credentials, store credentials, reviewer passwords, Firebase secrets,
  and private keys are never modeled as repository data.
- Every launch gate has an owner, evidence, status, and decision history.
- Privacy disclosures are treated as contracts that must match the shipped app.

## Entities

### LaunchCandidate

Represents a platform-specific build intended for beta, review, or public
release.

**Fields**:

- `candidateId`
- `platform`: iOS, Android
- `appVersion`
- `buildNumber`
- `bundleOrApplicationId`
- `releaseChannel`: local, beta, review, production
- `configurationProfile`: development, staging, production
- `buildArtifactLocation`: non-secret reference only
- `createdAt`
- `createdBy`
- `readinessStatus`: draft, betaReady, submissionReady, submitted, approved, released, blocked
- `blockingIssueIds`

**Validation**:

- Platform, version, build number, and app identity are required.
- Production candidates must reference production configuration.
- Build artifact references must not contain credentials or private URLs with
  embedded secrets.

**Relationships**:

- Has many `ReleaseGateResult` records.
- Has one or more `BetaTestCycle` records.
- Belongs to one `LaunchReadinessReport`.

### StoreListingPackage

Represents store-facing listing content for one platform.

**Fields**:

- `listingPackageId`
- `platform`: Apple App Store, Google Play
- `appName`
- `subtitleOrShortDescription`
- `fullDescription`
- `category`
- `keywordsOrTags`
- `supportUrl`
- `privacyPolicyUrl`
- `marketingUrl`
- `releaseNotes`
- `screenshotSetIds`
- `appIconStatus`
- `reviewNotes`
- `lastReviewedAt`
- `status`: draft, ready, submitted, needsRevision

**Validation**:

- Required store fields must be complete before submission.
- Screenshots and release notes must match the build being submitted.
- Support and privacy URLs must be publicly reachable.

**Relationships**:

- References `ReviewerAccessPlan`.
- References `PrivacyDisclosure`.
- Has many `StoreReviewIssue` records.

### PrivacyDisclosure

Represents Apple App Privacy and Google Play Data Safety/App Content answers.

**Fields**:

- `privacyDisclosureId`
- `platform`
- `dataCategories`
- `collectionPurposes`
- `dataLinkedToUser`
- `dataSharedWithThirdParties`
- `trackingOrCrossAppUse`
- `retentionSummary`
- `deletionPath`
- `thirdPartyServices`
- `reviewedBy`
- `reviewedAt`
- `status`: draft, ready, needsRevision, blocked

**Validation**:

- Every app data flow must map to a disclosure answer.
- Diagnostics, crash reporting, performance monitoring, authentication, support,
  account deletion, and backend storage must be reviewed.
- Disclosure answers must be re-reviewed after data-flow or dependency changes.

### ReviewerAccessPlan

Represents safe reviewer access to authenticated app features.

**Fields**:

- `reviewerAccessPlanId`
- `platform`
- `accessMethod`: revocableTestAccount, demoMode, seededReviewLeague
- `instructions`
- `testDataScope`
- `credentialStorageOwner`
- `expiresOrRevokesAt`
- `status`: draft, ready, revoked, blocked

**Validation**:

- Credentials must not be committed to the repository.
- Reviewer access must reach sign-in, scoring, recovery, privacy/support, and
  summary flows.
- Access must be revocable after review.

### BetaTestCycle

Represents an internal or external beta validation cycle.

**Fields**:

- `betaCycleId`
- `platforms`
- `candidateIds`
- `testerCount`
- `deviceCoverage`
- `journeysCovered`
- `startDate`
- `endDate`
- `crashFreeSessionRate`
- `feedbackSummary`
- `knownIssueIds`
- `status`: planned, running, complete, blocked

**Validation**:

- Critical journeys must include sign-in, standalone scoring, recovery, practice,
  summary, privacy/support access, and sign-out.
- Final cycle must record crash-free evidence or an owner-approved alternate
  measure if volume is too low.

### ReleaseGate

Defines a launch criterion that must pass or be owner-deferred.

**Fields**:

- `gateId`
- `name`
- `category`: build, security, privacy, identity, store, beta, accessibility, performance, monitoring, support
- `severityIfFailed`: critical, high, medium, low
- `owner`
- `evidenceRequired`
- `blockingRule`

**Validation**:

- Critical gates cannot be auto-deferred.
- Every gate must define evidence and an owner.

### ReleaseGateResult

Records a launch candidate's status for a gate.

**Fields**:

- `gateResultId`
- `candidateId`
- `gateId`
- `status`: notStarted, passing, failed, deferred, notApplicable
- `evidenceLinks`
- `decisionNotes`
- `decidedBy`
- `decidedAt`

**Validation**:

- Failed critical gates block submission.
- Deferred gates require owner approval and follow-up.

### LaunchReadinessReport

Represents the final release decision package.

**Fields**:

- `reportId`
- `candidateIds`
- `storeListingPackageIds`
- `privacyDisclosureIds`
- `betaCycleIds`
- `gateSummary`
- `knownRisks`
- `releaseDecision`: hold, submit, release, stagedRollout, rollback
- `approvedBy`
- `approvedAt`
- `monitoringPlanId`

**Validation**:

- Report cannot be approved while critical blockers remain.
- Report must include support owner, launch monitoring thresholds, and hotfix or
  rollback criteria.

### StoreReviewIssue

Represents app review feedback, rejection, metadata rejection, or policy
question from a store.

**Fields**:

- `storeReviewIssueId`
- `platform`
- `candidateId`
- `listingPackageId`
- `receivedAt`
- `issueType`: appAccess, metadata, privacy, policy, technicalQuality, crash, other
- `severity`
- `storeMessageSummary`
- `owner`
- `correctiveAction`
- `status`: open, inProgress, resolved, resubmitted, accepted
- `responseDueAt`

**Validation**:

- Every store issue requires owner and corrective-action plan within one
  business day.
- Resubmission requires evidence that the issue was addressed.

### LaunchMonitoringPlan

Represents first-release operational monitoring.

**Fields**:

- `monitoringPlanId`
- `releaseWindowStart`
- `releaseWindowEnd`
- `signals`: crash rate, sign-in failure, score-save failure, backend error, support volume
- `thresholds`
- `reviewCadence`
- `supportOwner`
- `engineeringOwner`
- `escalationPath`
- `hotfixCriteria`
- `rollbackCriteria`

**Validation**:

- Monitoring plan is required before public release.
- Severe signal triage target must be no longer than one business day.

## State Transitions

### LaunchCandidate

```text
draft -> betaReady -> submissionReady -> submitted -> approved -> released
draft -> blocked
betaReady -> blocked
submissionReady -> blocked
submitted -> blocked
blocked -> draft
blocked -> betaReady
blocked -> submissionReady
approved -> released
released -> blocked
```

Rules:

- `submissionReady` requires all critical gates passing or approved deferrals.
- `released` requires store approval and launch readiness report approval.
- `blocked` requires a blocking issue reference.

### StoreListingPackage

```text
draft -> ready -> submitted
draft -> needsRevision
ready -> needsRevision
submitted -> needsRevision
needsRevision -> ready
```

Rules:

- `ready` requires complete metadata, screenshots, support URL, privacy policy
  URL, release notes, review notes, and matching privacy disclosure.

### PrivacyDisclosure

```text
draft -> ready
draft -> needsRevision
ready -> needsRevision
needsRevision -> ready
ready -> blocked
blocked -> needsRevision
```

Rules:

- Any new data collection, dependency, diagnostic event, support flow, or
  deletion path change returns the disclosure to `needsRevision`.

### BetaTestCycle

```text
planned -> running -> complete
planned -> blocked
running -> blocked
blocked -> planned
blocked -> running
```

Rules:

- `complete` requires recorded coverage, results, known issues, and release
  gate impact.

### StoreReviewIssue

```text
open -> inProgress -> resolved -> resubmitted -> accepted
open -> resolved
inProgress -> open
resubmitted -> open
```

Rules:

- Owner and corrective action are required within one business day of `open`.

## Derived Values

- `criticalBlockerCount`: Count of failed critical release gates and open
  critical store issues.
- `submissionReady`: True when build, privacy, identity, store listing,
  accessibility, beta, and monitoring gates pass or have approved deferrals.
- `launchReady`: True when submission-ready candidates are approved by stores,
  launch report is approved, and monitoring plan is active.
- `privacyDisclosureStale`: True when data inventory or dependency review is
  newer than the disclosure review timestamp.
