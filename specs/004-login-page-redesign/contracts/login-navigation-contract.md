# Contract: Login Navigation

Feature: `004-login-page-redesign`

## Entry Points

- Cold unauthenticated launch routes from loading to login.
- Session-expired protected route redirects to login with a safe session-expired message.
- Sign-out redirects to login and clears authenticated navigation state.

## Exit Points

- Successful Google SSO routes to authenticated home or a safe preserved route intent.
- Successful Facebook SSO routes to authenticated home or a safe preserved route intent.
- Provider cancellation remains on login.
- Provider failure remains on login.
- Offline state remains on login.
- Help/support links may open approved HTTPS destinations or an in-app support route.

## Preserved Route Intent

The login page may preserve a requested authenticated route if all conditions are true:

- The route is a known app route.
- The route does not encode sensitive data in the route string.
- The route is still allowed after authentication.
- The user has not explicitly signed out.

If any condition fails, successful sign-in routes to authenticated home.

## Back Behavior

- Pressing system back from login after cold launch may exit the app.
- Pressing system back from provider failure or cancellation should keep the login route stable unless Android exits the app from the root task.
- Returning from support/privacy/account deletion should restore the login page state when possible.

## Protected Route Enforcement

- Unauthenticated access to Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings must redirect to login.
- Login redesign must not create a bypass around existing route protection.
- Session expiration during an authenticated flow must clear protected UI and return to login.
