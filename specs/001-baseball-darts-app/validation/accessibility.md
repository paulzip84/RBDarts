# Accessibility Review

## Current Coverage

- Score entry uses explicit 0 through 9 controls with stable button sizing.
- iOS score entry uses native SwiftUI controls and Dynamic Type-compatible text.
- Android score entry uses Material 3 buttons and text.
- Locked/correction states are represented with text, not color alone.
- Summary values are rendered as text and monospaced digits where useful.

## Required Pre-Release Checks

- Add accessibility identifiers for key iOS UI test interactions.
- Add Compose semantics for score buttons, undo, complete game, and correction
  reason fields.
- Verify VoiceOver and TalkBack navigation order on score entry and scorecards.
- Verify large text does not obscure score buttons on phone viewports.
