# Data Model: Android Material You App Experience

**Feature**: `003-android-material-you-ui`

## LaunchPresentationState

Represents startup branding and routing readiness.

- `appName`: Display text, expected `RBDarts`.
- `versionLabel`: User-visible app version/build.
- `imageAsset`: Committed Android-compatible loading image.
- `isLoading`: Whether startup/session resolution is in progress.
- `routeDecision`: Unknown, unauthenticated, authenticated, or blocked.
- `errorMessage`: Privacy-safe startup error text if routing cannot complete.

## AuthUiState

Represents login/sign-up/sign-out UI state.

- `sessionStatus`: Unauthenticated, signingIn, authenticated, expired, signingOut, failed.
- `selectedProvider`: Google, Facebook, or none.
- `displayName`: Optional non-sensitive profile display name.
- `providerError`: Privacy-safe provider failure category.
- `canRetry`: Whether retry action is available.
- `supportLinksVisible`: Whether privacy/support/account deletion links are shown.

## AppDestination

Represents top-level and nested navigation destinations.

- `routeId`: Stable route identity.
- `label`: User-visible label.
- `icon`: Material icon or branded asset reference.
- `requiresAuth`: Whether route is protected.
- `topLevel`: Whether route appears in primary navigation.
- `stateRestorationKey`: Key for restoring destination state.
- `adaptivePlacement`: Compact, medium, expanded navigation placement.

## GameTypeOption

Represents a selectable game or scoring mode.

- `gameTypeId`: Stable identity.
- `name`: User-visible name.
- `description`: Short purpose description.
- `playerMode`: Single, multiplayer, team, league, or practice.
- `seasonRequired`: Whether a season must be selected.
- `handicapSupported`: Whether player handicaps apply.
- `rulesSummary`: User-visible scoring rule summary.

## PlayerProfileDraft

Represents player creation and editing state.

- `playerId`: Optional existing player id.
- `displayName`: Required user-visible name.
- `status`: Active, inactive, archived.
- `seedAverage`: Optional initial average for handicaps.
- `currentAverage`: Derived from official history where available.
- `validationErrors`: Field-level errors.

## SeasonDraft

Represents season creation and editing state.

- `seasonId`: Optional existing season id.
- `name`: Required season name.
- `startDate`: Optional or required depending on season type.
- `endDate`: Optional end date.
- `status`: Draft, active, completed, archived.
- `rulesSummary`: League/season scoring context.
- `validationErrors`: Field-level errors.

## PlayerHandicapState

Represents individual player handicap display and edit state.

- `playerId`: Player identity.
- `displayName`: Player label.
- `sourceAverage`: Current average used for calculation.
- `configuredHandicap`: Editable value where authorized.
- `derivedHandicap`: Calculated game impact.
- `effectiveDate`: Date/time value applies from.
- `canEdit`: Permission-aware edit flag.
- `lastUpdatedBy`: Audit display summary.
- `validationErrors`: Field-level errors.

## ScoringUiState

Represents robust baseball darts score entry state.

- `sessionId`: Active scoring session id.
- `gameType`: Selected game type.
- `seasonId`: Optional season context.
- `participants`: Ordered players or teams.
- `activeInning`: Current inning/target.
- `scoreEntries`: Valid entered scores by participant and inning.
- `entryDraft`: Current unsaved input.
- `runningTotals`: Raw totals.
- `handicapAdjustedTotals`: Derived totals when applicable.
- `validationError`: Current entry error if any.
- `saveStatus`: Idle, saving, saved, failed, offlineQueued.
- `completionStatus`: Active, readyToComplete, completed, locked.
- `recoveryStatus`: None, restored, conflict, failed.

## Relationships

- `AuthUiState` gates all protected `AppDestination` values.
- `GameTypeOption`, `PlayerProfileDraft`, `SeasonDraft`, and `PlayerHandicapState` feed `ScoringUiState`.
- `PlayerHandicapState` can affect future `ScoringUiState` calculations but must not mutate completed or locked sessions.
- `LaunchPresentationState` routes into either `AuthUiState` or the authenticated app shell.
