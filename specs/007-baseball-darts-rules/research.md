# Research: Baseball Darts Rules

## Decision 1: Dart-Level Records Are The Source Of Truth

**Decision**: Represent each throw with a dart-level result and derive turn, inning, participant, and team totals from those records.

**Rationale**: The current model accepts only a whole inning score from 0 through 9. That is fast, but it cannot prove whether the inning total came from valid singles/doubles/triples, misses, bounce-outs, wrong-number throws, or corrections. Dart-level records support audit history, analytics such as triple rate, and a more robust scoring UI.

**Alternatives Considered**:

- Keep only inning totals. Rejected because it weakens auditability and analytics.
- Store both throws and editable inning totals as independent truth. Rejected because divergence would create integrity risk.

## Decision 2: Baseball Rule Set Is First-Class Configuration

**Decision**: Add a `BaseballRuleSet` concept with standard defaults: 9 regulation innings, 3 darts per player per inning, single/double/triple worth 1/2/3, bullseye bonus off, 7th inning stretch off, handicap off by default, and extra innings starting at target 10.

**Rationale**: The ruleset must support official league defaults, casual games, and optional house rules without hardcoding every choice into UI flows.

**Alternatives Considered**:

- Hardcode standard rules only. Rejected because the spec requires configurable optional rules.
- Let each screen maintain rule flags independently. Rejected because inconsistent rule interpretation would be likely across Android, iOS, standalone games, and league matches.

## Decision 3: Extra-Inning Target Resolution Is A Pure Rule Function

**Decision**: Implement a pure target resolver. Innings 1 through 20 target their matching number. After 20, the default is repeat 20 until the tie is broken; alternate configurations may restart at 1 or use a bullseye tiebreaker.

**Rationale**: Target resolution is scoring-critical and must behave identically on both platforms. Making it pure keeps it easy to test, fixture, and reuse in score entry UI.

**Alternatives Considered**:

- Stop all games after the 20th inning. Rejected because the user requested continued tiebreaking.
- Always restart at 1 after 20. Rejected because the recommended default is repeat 20s.

## Decision 4: Equal-Turn Extra Innings Gate Winner Resolution

**Decision**: A winner may be declared in extra innings only after every participant or team has completed the same extra inning.

**Rationale**: This matches the app-ready ruleset and prevents premature wins after the first team throws in an extra inning.

**Alternatives Considered**:

- Declare a winner immediately when a leader emerges in extras. Rejected because it violates the equal-turn rule.

## Decision 5: Official League Changes Require Immutable Audit Records

**Decision**: Score corrections, locked-game edits, and official substitutions must record actor, role, timestamp, original value, new value, reason, affected records, and authorization outcome.

**Rationale**: Security is the highest priority. League results need traceability and controlled correction behavior, especially after games are locked or finalized.

**Alternatives Considered**:

- Let scorekeepers overwrite scores while a game exists. Rejected because it loses accountability.
- Require manager approval for every active-game correction. Deferred as a configurable league policy; active-game correction may be allowed when the role policy permits it.

## Decision 6: Cross-Platform Parity Uses Contracts And Fixtures

**Decision**: Keep separate Kotlin and Swift implementations, and prove parity through shared Markdown contracts plus JSON fixtures in `shared-contracts/`.

**Rationale**: The constitution requires native platform architecture. Contracts let the code stay idiomatic while keeping scoring outcomes identical.

**Alternatives Considered**:

- Introduce a shared Kotlin Multiplatform or C++ rules engine. Rejected for this feature because it adds architectural weight and weakens native simplicity.

## Decision 7: Analytics Are Derived And Labeled

**Decision**: Compute analytics from recorded scoring data and label predictive or probability-based metrics as estimates. Show unavailable states when insufficient data exists.

**Rationale**: Metrics such as projected final score and comeback probability are useful, but they must not imply certainty. Derived analytics also avoid collecting unnecessary personal data.

**Alternatives Considered**:

- Store analytics snapshots as primary data. Rejected because derived values can drift and increase persistence complexity.
