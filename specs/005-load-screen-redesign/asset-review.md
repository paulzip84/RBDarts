# Asset Review: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## Source Asset

- Source path: `/Users/paulzip84/Downloads/RBDarts_Login`
- Source format: PNG
- Source dimensions: 1280 x 2856
- Source alpha: none
- Source size: 2.9 MB
- Visual source: Dartboard background, red dart flights, and white RBDarts mascot/mark.

## Runtime Asset

- Runtime path: `android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg`
- Runtime resource name: `rbdarts_load_background`
- Runtime format: JPEG
- Runtime dimensions: 806 x 1800
- Runtime size: 347 KB
- Android packaging: local `drawable-nodpi`, no runtime network fetch.
- Fallback resource: `android/app/src/main/res/drawable/rbdarts_loading_mark.xml`

## Optimization Command

```bash
sips -Z 1800 -s format jpeg -s formatOptions 82 \
  /Users/paulzip84/Downloads/RBDarts_Login \
  --out android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg
sips -d profile android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg
```

## Focal Region

- Primary focal region: Center portrait composition.
- Preserve where practical: dartboard texture, red dart flights, white mascot/hat mark.
- Text safe region: bottom overlay with dark scrim and centered brand/status text.
- Crop mode: Compose `ContentScale.Crop`, centered alignment.

## Safety Checklist

- [x] Runtime asset is local and packaged with the APK.
- [x] Runtime asset does not require network access.
- [x] Runtime asset is smaller than the raw source image.
- [x] Runtime asset uses a supported Android resource format.
- [x] Fallback vector is available if primary artwork is disabled.
- [x] Final secret, metadata, and remote-reference scan recorded.

## Scan Results

- Source string scan matched Adobe XMP namespace metadata in the source PNG, including `http://ns.adobe.com/...`. This source file is not packaged in the Android runtime.
- Runtime derivative string scan found no matches for `token`, `secret`, `password`, `http://`, `https://`, `firebase`, `client_secret`, `private_key`, or `session`.
- Runtime derivative still reports standard JPEG/JFIF/Exif structure through `file`, with no matched remote references or credential material.

## Notes

WebP was considered, but local tooling available in this environment can read WebP and cannot write WebP. JPEG was selected because the source has no alpha channel, the visual is photographic, and the derivative is substantially smaller than the source PNG.
