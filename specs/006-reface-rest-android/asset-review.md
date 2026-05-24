# Asset Review: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Local Vector Icons

- `ic_rbdarts_menu.xml`
- `ic_rbdarts_home.xml`
- `ic_rbdarts_game_type.xml`
- `ic_rbdarts_players.xml`
- `ic_rbdarts_seasons.xml`
- `ic_rbdarts_handicaps.xml`
- `ic_rbdarts_scoring.xml`
- `ic_rbdarts_settings.xml`
- `ic_rbdarts_sign_out.xml`

## Review Notes

- Icons are local Android vector drawables.
- Icons do not include remote references, embedded metadata, user data, provider ids, or secrets.
- Runtime color is controlled by Compose Material 3 drawer item state.
- No APK-size-sensitive raster artwork was added for this feature.
