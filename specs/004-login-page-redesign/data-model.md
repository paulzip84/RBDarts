# Data Model: Login Page Visual Redesign

Feature: `004-login-page-redesign`

## LoginPresentation

Represents the display-safe state needed to render the redesigned login surface.

Fields:

- `brandName`: Display name, expected `RBDarts`.
- `headline`: Primary welcome text, expected to use "Welcome back" or equivalent.
- `supportingText`: Concise SSO sign-in explanation.
- `visualStyle`: `DarkPremium`, `LightFallback`, or `DynamicColor`.
- `logoAsset`: Local app logo or RBDarts mark identifier.
- `providerActions`: Ordered list of `SsoProviderAction`.
- `supportLinks`: Ordered list of `AccountSupportLink`.
- `message`: Optional `LoginMessage`.
- `preservedRouteIntent`: Optional authenticated destination requested before sign-in.

Rules:

- Must not include email, password, raw provider payload, provider token, or first-party credential fields.
- Must support compact and expanded layouts.
- Must keep provider actions visually stable while loading or failing.

## SsoProviderAction

Represents a single provider button on the login page.

Fields:

- `providerId`: `Google` or `Facebook`.
- `label`: User-visible action text, for example `Continue with Google`.
- `brandAssetState`: `Available`, `MissingFallback`, or `Unavailable`.
- `availability`: `Available`, `Unavailable`, `RequiresNetwork`, or `NotConfigured`.
- `interactionState`: `Idle`, `Loading`, `Succeeded`, `Cancelled`, or `Failed`.
- `accessibilityLabel`: Full screen-reader label.
- `retryAllowed`: Boolean.

Rules:

- Only the selected provider may show loading state.
- Disabled providers must explain unavailable status without exposing provider internals.
- Provider labels and assets must follow brand rules and remain readable in dark theme.

## LoginMessage

Represents a safe message shown on the login page.

Fields:

- `type`: `Info`, `Warning`, `Error`, or `Success`.
- `title`: Short display text.
- `body`: Privacy-safe explanation.
- `actionLabel`: Optional support or retry label.
- `reasonCode`: Internal coarse code such as `provider_unavailable`, `offline`, or `session_expired`.

Rules:

- Must not include raw provider error, email address, token, or account identifier.
- Must allow retry when the failure is recoverable.

## AccountSupportLink

Represents a secondary account or policy action.

Fields:

- `kind`: `SignInHelp`, `Support`, `PrivacyPolicy`, or `AccountDeletion`.
- `label`: User-visible text.
- `url`: HTTPS destination from release configuration or approved support route.
- `prominence`: `Inline`, `Secondary`, or `Overflow`.

Rules:

- Must not be more visually dominant than provider sign-in.
- Must remain reachable on compact screens and with large font.

## LoginDiagnosticEvent

Represents privacy-safe observability for the login page.

Fields:

- `name`: Event name, such as `android_login_viewed`, `android_login_provider_selected`, or `android_login_provider_failed`.
- `provider`: Optional coarse provider id.
- `status`: Optional coarse status.
- `reasonCode`: Optional safe reason code.

Rules:

- Must not include raw provider response, email, token, provider account id, user id, or contact data.
- Must be covered by redaction tests.

## State Transitions

```text
Login shown
  -> Provider selected
  -> Provider loading
  -> Authenticated home

Provider loading
  -> Cancelled
  -> Login shown with retry

Provider loading
  -> Failed or unavailable
  -> Login shown with safe message and retry

Login shown
  -> Need help signing in
  -> Support or account access destination
```
