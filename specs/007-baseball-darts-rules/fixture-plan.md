# Fixture Plan: Baseball Darts Rules

## Shared Schemas

- `shared-contracts/schemas/scoring-fixture.schema.json` now supports legacy inning-total fixtures and new dart-level turn fixtures.
- `shared-contracts/schemas/security-rule-fixture.schema.json` now supports correction, lock, unlock, and substitution operations.

## Added Fixtures

- `shared-contracts/fixtures/scoring/baseball-standard-ruleset.json`
- `shared-contracts/fixtures/scoring/baseball-standard-nine-innings.json`
- `shared-contracts/fixtures/scoring/baseball-invalid-darts.json`
- `shared-contracts/fixtures/scoring/baseball-team-scoring.json`
- `shared-contracts/fixtures/scoring/baseball-extra-innings-repeat20.json`
- `shared-contracts/fixtures/scoring/baseball-post20-tiebreakers.json`
- `shared-contracts/fixtures/corrections/baseball-locked-correction.json`
- `shared-contracts/fixtures/corrections/baseball-correction-and-substitution.json`

## Coverage

- Standard target mapping and three-dart turn scoring.
- Miss, wrong-number, bounce-out, and zero-score outcomes.
- Team totals from player turns.
- Extra-inning continuation and post-20 target modes.
- Locked-game manager correction and substitution authorization examples.

## Remaining Expansion

- Add full 20-inning generated fixtures if a future validator consumes every inning turn explicitly.
- Add separate fixtures for 7th-inning-stretch, mercy rule, handicap, and bullseye bonus once those optional rules become productized.
