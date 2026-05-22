<!--
Sync Impact Report
Version change: unratified template -> 1.0.0
Modified principles:
- Template principle 1 -> Security and Privacy First
- Template principle 2 -> Native Platform Architecture
- Template principle 3 -> Trusted SSO Identity
- Template principle 4 -> Performance and Stability Budgets
- Template principle 5 -> Testable Quality and Privacy-Safe Observability
Added sections:
- Mobile Platform Standards
- Development Workflow and Quality Gates
Removed sections:
- Placeholder SECTION_2_NAME
- Placeholder SECTION_3_NAME
Templates requiring updates:
- .specify/templates/plan-template.md: updated
- .specify/templates/spec-template.md: updated
- .specify/templates/tasks-template.md: updated
Runtime guidance reviewed:
- README.md: updated
- AGENTS.md: updated
Follow-up TODOs: none
-->

# RBDarts Constitution

## Core Principles

### I. Security and Privacy First
Security is the highest priority for RBDarts and MUST take precedence over
feature scope, delivery speed, analytics, and convenience. Every feature MUST
identify the user data it touches, the trust boundaries it crosses, the threats
it introduces, and the mitigations required before implementation starts.
Sensitive data MUST be minimized, encrypted in transit and at rest where the
platform supports it, and excluded from logs, crash reports, analytics, and test
fixtures unless explicitly redacted. Secrets, tokens, private keys, and signing
materials MUST NOT be committed to the repository.

Rationale: A darts app with accounts and match history still handles identity,
profile, and usage data. Trust is a core product requirement, not a later
hardening pass.

### II. Native Platform Architecture
The iOS app MUST be built with native Swift practices and the Android app MUST
be built with native Kotlin practices. Platform code MUST respect each platform's
recommended lifecycle, concurrency, accessibility, navigation, persistence, and
testing patterns. Shared behavior may be expressed through specifications,
contracts, design documents, and test scenarios, but production application code
MUST NOT force a lowest-common-denominator architecture across iOS and Android.

iOS plans MUST document the chosen Swift architecture and module boundaries,
including state management, dependency injection, async work, secure storage,
and test strategy. Android plans MUST document the chosen Kotlin architecture and
module boundaries, including state management, coroutine or asynchronous work,
secure storage, lifecycle handling, and test strategy.

Rationale: Native apps feel reliable when they fit the operating system, not
when each platform is treated as a skin over a generic model.

### III. Trusted SSO Identity
User authentication MUST use trusted SSO through established identity providers,
including Google and Facebook where product scope calls for social sign-in.
Authentication flows MUST use standards-based delegated authorization and sign-in
patterns appropriate to each provider and platform. RBDarts MUST NOT collect,
store, or validate user passwords directly unless a future constitution amendment
explicitly approves a first-party credential system with a documented security
model.

Access tokens, refresh tokens, ID tokens, and session state MUST be stored only
in platform-approved secure storage. Sign-in, sign-out, account linking, token
refresh, token revocation, provider failure, and user cancellation flows MUST be
specified and tested. Authorization checks MUST be explicit for any protected
profile, match, or history data.

Rationale: SSO reduces password handling risk, but only when token lifecycle and
account recovery behavior are designed deliberately.

### IV. Performance and Stability Budgets
Every user-facing feature MUST define measurable performance and stability
targets before implementation. Targets MUST cover the user experience affected by
the feature, including launch or entry latency, interaction responsiveness,
network timeout behavior, offline or degraded-state behavior where applicable,
memory impact, and crash-free expectations. Any feature that cannot meet its
targets MUST be simplified, deferred, or explicitly accepted as a documented
exception.

The apps MUST avoid avoidable main-thread blocking, unbounded work, uncontrolled
retry loops, battery-heavy background behavior, and memory growth that scales
without a clear bound. Error handling MUST preserve user data and allow safe
recovery from provider failures, network loss, app termination, and OS lifecycle
events.

Rationale: Darts scoring and match flows must feel instant and dependable during
live play, where crashes or lag directly undermine the product.

### V. Testable Quality and Privacy-Safe Observability
Security-sensitive, authentication-related, scoring-critical, persistence, and
cross-platform parity behavior MUST have automated tests before release. Plans
MUST identify the minimum test set for iOS and Android, including unit tests,
integration tests, and UI or end-to-end tests where user journeys require them.
Tests MUST cover failure paths as well as successful paths.

RBDarts MUST include privacy-safe diagnostics for crashes, errors, and critical
state transitions. Observability MUST help diagnose reliability problems without
capturing secrets, tokens, precise personal data, or unnecessary behavioral data.
Log and analytics events MUST be named, scoped, and reviewed as part of feature
planning.

Rationale: Stability work is only real when failures are visible, reproducible,
and safe to inspect.

## Mobile Platform Standards

RBDarts is a native mobile project with separate iOS and Android applications.
The iOS application MUST be implemented in Swift using Apple platform APIs and
current iOS architectural practices appropriate to the feature. The Android
application MUST be implemented in Kotlin using Android platform APIs and current
Android architectural practices appropriate to the feature.

Platform plans MUST document:

- Supported OS versions and device classes.
- App architecture, module boundaries, and dependency direction.
- UI framework and navigation approach.
- State management and asynchronous work model.
- Secure storage and cryptographic boundary decisions.
- Accessibility requirements for score entry and match review.
- Offline, background, and lifecycle behavior.
- Performance and stability budgets with verification method.

Third-party dependencies MUST be justified by product value, security posture,
maintenance status, license compatibility, and platform fit. Dependencies that
handle identity, storage, networking, cryptography, analytics, or crash reporting
require explicit security review before adoption.

## Development Workflow and Quality Gates

Every feature MUST pass these gates before implementation planning is accepted:

- Security gate: threat model, data classification, and mitigation plan are
  documented.
- Native architecture gate: iOS and Android implementation patterns are named
  and appropriate to each platform.
- Identity gate: SSO providers, token lifecycle, sign-out, revocation, and
  failure behavior are specified when authentication is involved.
- Performance and stability gate: measurable targets and verification approach
  are documented.
- Test and observability gate: required tests and privacy-safe diagnostics are
  documented.

Before release, features MUST demonstrate that these gates were implemented:

- Automated tests for security-sensitive, auth, scoring, persistence, and major
  user journey behavior pass on affected platforms.
- Manual or automated validation covers provider cancellation, network failure,
  app restart, and session expiration where relevant.
- Crash, error, and performance instrumentation is reviewed for privacy.
- Known exceptions are documented with owner, rationale, risk, and follow-up.

## Governance

This constitution supersedes conflicting implementation preferences, generated
plans, tasks, and informal guidance. Any feature plan or task list that violates
the constitution MUST either be revised or include a documented exception with
security, privacy, stability, and product rationale.

Amendments require:

- A written summary of the change and reason.
- A semantic version decision.
- Review of affected Speckit templates, specs, plans, and tasks.
- Migration notes for any active feature work affected by the change.

Versioning policy:

- MAJOR: Removes or redefines a core principle or lowers a mandatory gate.
- MINOR: Adds a principle, section, gate, or materially expands requirements.
- PATCH: Clarifies wording without changing governance meaning.

Compliance review MUST occur during `$speckit-plan`, `$speckit-tasks`, and before
release. Reviewers MUST treat unresolved security, auth, data protection,
performance, stability, and test gaps as blocking unless an exception is
explicitly accepted.

**Version**: 1.0.0 | **Ratified**: 2026-05-22 | **Last Amended**: 2026-05-22
