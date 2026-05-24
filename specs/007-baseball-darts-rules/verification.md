# Verification: Baseball Darts Rules

## 2026-05-24

- `cd android && ./gradlew testDebugUnitTest --tests com.rbdarts.core.domain.BaseballDartsRulesTest`
  - Initial sandbox run failed because Gradle needed access to `~/.gradle`.
  - Re-run with Android Studio JBR and local SDK succeeded after fixing a Kotlin brace error.
  - Result: PASS.
- `cd android && ./gradlew testDebugUnitTest --tests com.rbdarts.core.domain.BaseballDartsRulesTest --tests com.rbdarts.materialyou.ScoringUiStateTest --tests com.rbdarts.materialyou.ScoringValidationUiTest`
  - Result: PASS.
- `cd android && ./gradlew testDebugUnitTest`
  - Result: PASS.
- `cd android && ./gradlew compileDebugAndroidTestKotlin`
  - Result: PASS.
- `cd android && ./gradlew lintDebug`
  - Result: PASS.
- `cd android && ./gradlew assembleDebug`
  - Result: PASS.
- `xcodebuild test -project ios/RBDarts.xcodeproj -scheme RBDarts -destination 'platform=iOS Simulator,name=iPhone 16' -only-testing:RBDartsTests/ScoringRulesTests`
  - Blocked: active developer directory is `/Library/Developer/CommandLineTools`, and full Xcode is required for `xcodebuild`.
- `xcrun swiftc -module-cache-path /private/tmp/rbdarts-swift-module-cache -typecheck ios/RBDarts/Shared/Domain/Models.swift ios/RBDarts/Shared/Domain/ScoringRules.swift ios/RBDarts/Shared/Observability/Diagnostics.swift`
  - Result: PASS.
- JSON schema/fixture parse check with Node for updated scoring/security schemas and new Baseball Darts fixtures.
  - Result: PASS.

## Pending

- iOS XCTest run.
