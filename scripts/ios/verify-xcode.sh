#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
PROJECT_PATH="$PROJECT_ROOT/ios/RBDarts.xcodeproj"
SCHEME="${IOS_SCHEME:-RBDarts}"
DESTINATION="${IOS_DESTINATION:-platform=iOS Simulator,name=iPhone 16}"

if ! xcodebuild -version >/dev/null 2>&1; then
  echo "xcodebuild is not available. Install full Xcode and select it with:" >&2
  echo "  sudo xcode-select -s /Applications/Xcode.app/Contents/Developer" >&2
  exit 1
fi

if [[ ! -d "$PROJECT_PATH" ]]; then
  echo "Missing Xcode project: $PROJECT_PATH" >&2
  exit 1
fi

xcodebuild -list -project "$PROJECT_PATH"
xcodebuild test \
  -project "$PROJECT_PATH" \
  -scheme "$SCHEME" \
  -destination "$DESTINATION"
