# Public URL Verification

Status: Syntax validated locally; live reachability requires public site deployment.

## URLs

- Support: https://rbdarts.app/support
- Privacy Policy: https://rbdarts.app/privacy
- Account Deletion: https://rbdarts.app/account/delete

## Validation Command

```bash
node scripts/launch/check-public-urls.mjs launch/privacy/public-url-verification.md launch/app-store/metadata/en-US.json launch/google-play/metadata/en-US.json
```

Set `CHECK_PUBLIC_URLS=1` only after the public website is deployed and expected to respond.

## Result

- Syntax validation: passed locally, 9 valid URLs.
- Reachability validation: pending public URL deployment.
