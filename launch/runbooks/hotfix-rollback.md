# Hotfix And Rollback Criteria

Status: Draft.

## Hotfix Triggers

- Critical crash in sign-in, scoring, recovery, or completion.
- Scoring data corruption or repeatable data loss.
- Broken account deletion path.
- Production configuration regression.
- Security or privacy defect.

## Rollback Or Halt Triggers

- Public rollout has started and a critical issue affects multiple users.
- Store review identifies a policy blocker that cannot be fixed with metadata.
- Backend dependency is unavailable and no safe degradation exists.
- SSO provider configuration breaks sign-in for reviewers or users.

## Decision Owners

- Release owner approves hotfix release.
- Engineering owner approves technical mitigation.
- Support owner coordinates user communication.
