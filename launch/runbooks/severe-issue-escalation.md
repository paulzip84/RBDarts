# Severe Issue Escalation Runbook

Status: Draft.

## Trigger

Escalate immediately for any security/privacy concern, sign-in outage, scoring data loss, account deletion failure, or reproducible crash in the active scoring path.

## Steps

1. Assign incident owner.
2. Record platform, app build, device, OS, and reproduction path.
3. Confirm whether the issue affects beta, review, or public users.
4. Pause rollout if user harm or data risk is plausible.
5. Prepare hotfix or rollback decision.
6. Communicate status to support owner and store review owner.

## Evidence

Record the decision in `launch/release-gates/decision-log.md`.
