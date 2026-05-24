# Evidence Template: US5 Robust Scoring

## Automated Coverage

- `ScoringUiStateTest`
- `ScoringValidationUiTest`
- `ScoringRecoveryStateTest`
- `RobustScoringScreenTest`
- `InvalidScoreEntryTest`
- `ScoringAccessibilityTest`
- `ScoringPerformanceSmokeTest`

## Manual Smoke

- Enter valid scores and confirm active player advances.
- Try an out-of-range value and confirm submit is blocked or rejected.
- Review raw and handicap-adjusted totals.
- Recover a sample active game.
- Complete the scorecard and confirm review state.
