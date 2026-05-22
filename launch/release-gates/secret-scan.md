# Secret Scan Evidence

Status: Draft.

## Protected Materials

- Apple signing certificates
- Provisioning profiles
- Android keystores
- Firebase production config files
- SSO provider console exports
- Reviewer credentials

## Repository Safeguards

- `.gitignore` excludes release config, key material, private artifacts, and credential-like launch files.
- Launch artifact validator rejects secret-like keys and values in JSON artifacts.
- Store access and reviewer access runbooks instruct owners to use platform consoles, not committed files.

## Commands

```bash
git status --short
node scripts/launch/validate-launch-artifacts.mjs
```

## Result

No private launch materials are intentionally committed. Final pass should include a dedicated repository secret scan before submission.
