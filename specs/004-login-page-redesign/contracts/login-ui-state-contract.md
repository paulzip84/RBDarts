# Contract: Login UI State

Feature: `004-login-page-redesign`

## Default Login State

Required visible content:

- RBDarts mark or approved logo.
- Heading: `Welcome back` or approved equivalent.
- Supporting copy that explains SSO sign-in to RBDarts.
- Google provider action.
- Facebook provider action.
- Provider-safe help action.
- Support, privacy policy, and account deletion access.

Forbidden content:

- Email input.
- Password input.
- Password reset action.
- Copy that implies RBDarts owns or can reset provider passwords.
- Raw provider error text.

## Provider Action States

| State | User-visible behavior | Retry |
| --- | --- | --- |
| Idle | Button is enabled and labeled with provider name | Yes |
| Loading | Selected button shows progress and all provider actions are protected from double-submit | No |
| Cancelled | Safe message says sign-in was cancelled and login remains interactive | Yes |
| Failed | Safe message says provider sign-in could not complete | Yes |
| Unavailable | Button is disabled or message explains provider is temporarily unavailable | Yes when provider returns |
| Offline | Message explains SSO requires connectivity | Yes after network returns |
| Session expired | Message explains the user needs to sign in again | Yes |

## Accessibility Requirements

- Provider buttons must have full labels, not icon-only semantics.
- Decorative divider, background, and glow elements must be hidden from screen readers.
- Error and offline messages must be announced as meaningful text.
- Touch targets must be at least 48dp.
- Text must support 200% font scaling without overlapping controls.

## Diagnostic Events

Allowed event names:

- `android_login_viewed`
- `android_login_provider_selected`
- `android_login_provider_succeeded`
- `android_login_provider_cancelled`
- `android_login_provider_failed`
- `android_login_help_selected`

Allowed attributes:

- `provider`: `google`, `facebook`, or omitted.
- `status`: coarse status such as `loading`, `success`, `cancelled`, `failed`.
- `reasonCode`: safe code such as `offline`, `provider_unavailable`, `session_expired`.

Disallowed attributes:

- `token`
- `email`
- `providerUserId`
- `rawProviderResponse`
- `password`
- `displayName`
- `userId`
