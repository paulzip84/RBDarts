# Contract: Android Navigation Map

**Feature**: `003-android-material-you-ui`

## Route Contract

| Route | Auth Required | Top Level | Purpose | Required States |
| --- | --- | --- | --- | --- |
| `loading` | No | No | Show branded startup, version, and route decision progress | Loading, route resolved, route failed |
| `login` | No | No | Google/Facebook sign-in/sign-up and recovery | Idle, signing in, cancelled, failed, offline |
| `home` | Yes | Yes | Authenticated landing summary and next actions | Empty, ready, recover scoring |
| `game-type` | Yes | Yes | Select baseball darts game mode | Empty, selectable, validation |
| `players` | Yes | Yes | Create/select players | Empty, form draft, saved, validation |
| `seasons` | Yes | Yes | Create/select seasons | Empty, form draft, saved, validation |
| `handicaps` | Yes | Yes | View/edit individual player handicaps | View only, editable, saved, validation |
| `scoring` | Yes | Yes | Score active game and complete scorecard | Setup needed, active, saving, recovered, completed |
| `stats-summary` | Yes | Yes | Review game or season summaries | Empty, loading, ready |
| `settings` | Yes | Yes | Privacy, support, account, sign-out | Ready, signing out, failed |

## Navigation Rules

- Loading is the first displayed route on cold start.
- Loading routes to login when no active authenticated session is available.
- Loading routes to home when a valid authenticated session is available.
- Login routes to home only after provider success.
- Protected routes route back to login if the session expires.
- System back from top-level authenticated destinations returns to prior destination or home according to Android expectations; it must not reveal protected content after sign-out.
- Game setup may deep-link into scoring only when required game type and participant state is valid.
- Handicap edits affect future scoring setup only after save confirmation.

## Adaptive Placement

- Compact phones: primary sections use bottom navigation or Material navigation bar with overflow through menu/settings where necessary.
- Medium/expanded screens: primary sections may use navigation rail or drawer to reduce crowding.
- Scoring must keep active inning, participants, entry controls, raw totals, and adjusted totals visible or one gesture away on all supported sizes.
