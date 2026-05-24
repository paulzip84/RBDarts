# Security Threat Model: Mobile App Store Launch Readiness

## Scope

Launch readiness touches production app identity, store metadata, privacy
disclosures, beta evidence, release candidates, reviewer access, support paths,
diagnostics, and public rollout monitoring for RBDarts.

## Sensitive Data

- Signing keys, provisioning profiles, keystores, app-specific passwords, and
  store credentials.
- Reviewer credentials and beta tester contact details.
- SSO provider configuration, production Firebase config, and backend access.
- User identity, league membership, scores, practice attempts, correction
  reasons, support messages, diagnostics tied to a user, and account deletion
  requests.

## Threats

- Committing signing material or reviewer credentials.
- Shipping a release candidate with development endpoints or sample secrets.
- Store privacy disclosures diverging from actual data collection.
- Reviewers blocked by unavailable or unsuitable sign-in instructions.
- Beta or production diagnostics leaking tokens, secrets, correction reasons, or
  unnecessary personal data.
- Public rollout proceeding with unresolved crash, auth, score-save, or data-loss
  defects.

## Mitigations

- Store all private credentials outside git and add launch-specific ignore
  patterns.
- Validate launch artifacts with local scripts and release gates.
- Maintain Apple and Google privacy disclosure drafts from the data inventory.
- Use revocable reviewer access and document setup outside credential files.
- Run beta, accessibility, recovery, and diagnostics privacy checks before
  public release.
- Block public release on non-deferrable critical gates.
