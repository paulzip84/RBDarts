# Launch Data Inventory

| Data | Collected | Linked To User | Shared | Purpose | Deletion Path |
|------|-----------|----------------|--------|---------|---------------|
| SSO identity id and provider ids | Yes | Yes | With identity provider and Firebase | Sign-in and account linking | Account deletion request |
| Display name and profile details | Yes | Yes | No external sharing beyond backend processing | Player and league display | Account/profile deletion request |
| League, team, role, match, game, and score history | Yes | Yes or league-linked | No external sharing beyond backend processing | Official scoring and standings | League/account deletion policy |
| Practice attempts | Yes | Yes | No external sharing beyond backend processing | Personal practice stats | Account deletion request |
| Correction reasons and audit records | Yes | Yes | No external sharing beyond backend processing | Official audit trail | Retained per league audit policy |
| Crash, error, and performance diagnostics | Yes | Pseudonymous where possible | Diagnostic providers | Stability and support | Diagnostic retention controls |
| Support and account deletion requests | Yes | Yes | Support tooling if configured | User support and privacy rights | Request closure/deletion policy |

## Review Notes

- No first-party password is collected.
- Diagnostics must not include tokens, secrets, unredacted correction reasons,
  reviewer credentials, or unnecessary personal data.
- This inventory must be re-reviewed before final store submission.
