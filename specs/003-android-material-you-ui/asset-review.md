# Asset Review: RBDarts Loading Image

Feature: `003-android-material-you-ui`

## Source

- Traceable source copy: `android/app/src/main/res/raw/rbdarts_loading_source.svg`
- Android-renderable fallback: `android/app/src/main/res/drawable/rbdarts_loading_mark.xml`

## Review Notes

- The app loads the local Android vector fallback at runtime, avoiding network image fetches on startup.
- The copied SVG is retained as design-source evidence only.
- The drawable uses app-owned vector paths and does not include external URLs, credentials, or runtime script execution.
- The image is decorative plus brand-identifying; the Compose loading screen supplies the accessibility description `RBDarts loading mark`.

## Launch Safety

- Do not include real player data, account identifiers, or provider logos in the loading asset unless store/provider brand rules are reviewed.
- Re-run the secret scan before release if the source SVG is replaced.
