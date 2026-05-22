# Production Rollout Decision Checklist

Status: Draft hold.

## Before Public Release

- App Store build approved.
- Google Play build approved.
- Beta gates passed or formally deferred.
- Public URLs reachable.
- Crash and performance monitoring confirmed.
- Support owner ready.
- Hotfix and rollback criteria reviewed.
- Decision log updated.

## Initial Rollout

- Start with controlled release if platform supports it.
- Review monitoring signals at the cadence in `launch/runbooks/launch-monitoring-plan.json`.
- Escalate any critical signal immediately.

## Current Decision

Hold public launch until signed build and beta evidence is attached.
