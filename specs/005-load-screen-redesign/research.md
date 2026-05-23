# Research: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## Decision 1: Use The Attached PNG As Source, Not The Raw Runtime Asset

**Decision**: Treat `/Users/paulzip84/Downloads/RBDarts_Login` as the visual source and create an optimized Android runtime derivative.

**Rationale**: The supplied PNG is 1280 x 2856 and about 3 MB. It is visually rich but larger than ideal for startup if packaged and decoded naively. An optimized derivative can preserve the darts artwork while reducing memory and APK impact.

**Alternatives Considered**:

- Use the raw PNG directly: rejected due to startup memory/APK bloat risk.
- Keep the existing vector-only load screen: rejected because it does not satisfy the requested full image redesign.
- Fetch the image remotely: rejected because startup must not depend on network or remote media.

## Decision 2: Full-Bleed Image With Scrim And Safe Text Region

**Decision**: Render the artwork full-bleed with content scaling/cropping and overlay a scrim or gradient safe region for `RBDarts`, version/build, and loading status.

**Rationale**: The image is busy, high contrast, and portrait-oriented. Without a scrim, text readability will vary by crop and device size. A safe text region lets the image remain immersive without sacrificing accessibility.

**Alternatives Considered**:

- Center text directly over image: rejected due to readability risk.
- Use image only with no text: rejected because version/build and loading status are required.
- Put image in a small card: rejected because the request is for a redesigned load screen and the asset works best as a full-bleed splash visual.

## Decision 3: Preserve Existing Startup Routing

**Decision**: Keep `StartupRouteController` and `RBDartsAppRoot` responsible for routing from loading to login or authenticated home.

**Rationale**: The load screen should not own authentication. Reusing existing routing reduces security risk and keeps this feature focused on presentation, feedback, and asset handling.

**Alternatives Considered**:

- Move auth/session decisions into `LoadingScreen`: rejected because it mixes presentation with identity decisions.
- Add provider error rendering to load screen: rejected because provider errors belong on login or authenticated flows.

## Decision 4: Fallback To Existing RBDarts Vector Mark

**Decision**: If the optimized bitmap cannot load or is disabled in tests, fall back to the existing local RBDarts vector mark and branded text.

**Rationale**: A load screen cannot go blank. The existing mark is already local, small, and known to render.

**Alternatives Considered**:

- Fail hard if image is missing: rejected because startup must remain stable.
- Use remote fallback: rejected due to network dependency and privacy risk.

## Decision 5: Asset Safety Review Is A Release Gate

**Decision**: Require metadata, secret, remote-reference, and image-size review for the source and optimized derivatives.

**Rationale**: User-supplied image assets can carry metadata or be too large. The project constitution requires secrets and sensitive data stay out of git, and launch assets should be safe to inspect.

**Alternatives Considered**:

- Trust image assets without review: rejected by security-first governance.

## Decision 6: Test The Load Screen At UI And Artifact Levels

**Decision**: Combine unit tests for state/routing/fallback with Compose UI tests for rendering/accessibility and file checks for asset size/type.

**Rationale**: Visual startup quality depends on both code behavior and packaged assets. Tests should catch broken version text, missing status, inaccessible labels, and accidental remote/oversized assets.

**Alternatives Considered**:

- Screenshot-only testing: useful but insufficient for contracts.
- Unit-only testing: insufficient for visual/adaptive rendering.
