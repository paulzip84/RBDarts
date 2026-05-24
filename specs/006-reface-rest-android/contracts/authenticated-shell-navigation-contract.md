# Contract: Authenticated Shell Navigation

Feature: `006-reface-rest-android`

## Shell Entry

- Authenticated screens must render inside the authenticated app shell.
- Loading and login screens remain outside this shell.
- The shell must show a top app bar with a top-left hamburger button.
- The hamburger button accessible label must be `Open navigation menu`.

## Removed Navigation

The authenticated shell must not show:

- Bottom navigation bar.
- Navigation rail.
- Login provider buttons.
- Password, password reset, or credential collection controls.

## Drawer Content

The hamburger navigation menu must include:

- Home
- Game Type
- Players
- Seasons
- Handicaps
- Scoring
- Settings
- Sign Out

Required behavior:

- Current route is visibly selected.
- Current route selected state is available to assistive technology.
- Selecting a destination closes the menu.
- Selecting Sign Out closes the menu, clears authenticated UI access, and routes to login.
- Back press closes the menu before applying normal app back behavior.

## Protected Routes

Protected route rules:

- Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings require authentication.
- Unauthenticated requests to protected routes must route to login.
- Preserved routes may be restored only when known, authenticated, and free of sensitive arguments.
- Sign-out must prevent protected content from remaining visible.

## Adaptive Behavior

Compact phones:

- Hamburger remains at the top left.
- Drawer opens over content and leaves primary screen content intact when closed.
- Menu labels fit or ellipsize without horizontal scrolling.

Large phones/tablets/foldables:

- Hamburger remains the primary navigation affordance.
- Drawer may use a wider sheet or adjusted content width.
- The old bottom bar and rail must not reappear.

## Diagnostics

Allowed event names:

- `android_nav_menu_opened`
- `android_nav_menu_closed`
- `android_nav_destination_selected`
- `android_nav_sign_out_selected`
- `android_nav_protected_redirect`

Allowed attributes:

- `destination`: `home`, `game_type`, `players`, `seasons`, `handicaps`, `scoring`, `settings`, or `login`.
- `menuState`: `opened` or `closed`.
- `result`: `selected`, `dismissed`, `sign_out`, or `redirected`.
- `timingBucket`: `lt_250ms`, `250_to_500ms`, or `gt_500ms`.

Disallowed attributes:

- `token`
- `email`
- `providerUserId`
- `rawProviderResponse`
- `userId`
- `displayName`
- `playerName`
- `rawScore`
- `sessionId`
- `debugLog`
