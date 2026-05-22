# Launch Evidence Workspace

This directory stores non-secret evidence for App Store and Google Play launch
readiness. It is safe to commit checklists, metadata drafts, privacy mappings,
beta summaries, release-gate results, and runbooks.

Do not commit signing keys, provisioning profiles, keystores, app-specific
passwords, reviewer passwords, production Firebase config files, private API
keys, or store-console credentials.

## Directory Guide

- `app-store/`: Apple metadata, screenshot checklists, and review notes.
- `google-play/`: Google Play metadata, screenshot checklists, and App Content notes.
- `privacy/`: Data inventory, privacy disclosure drafts, account deletion evidence, and diagnostics reviews.
- `beta/`: TestFlight and Google Play beta plans, device matrix, and feedback summaries.
- `release-gates/`: Launch candidate records, gate catalog, gate results, and readiness reports.
- `runbooks/`: Submission, monitoring, support, escalation, and rollback instructions.

Run local launch artifact validation with:

```bash
node scripts/launch/validate-launch-artifacts.mjs
node --test scripts/launch/validate-launch-artifacts.test.mjs
```
