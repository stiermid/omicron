# Omicron for Android

The Android client for [Omicron](https://github.com/the-jk-labs/omicron), a federated and self-hostable ActivityPub blogging platform.

This repository currently contains the Android-first Kotlin Multiplatform bootstrap. Android is the only active target; shared application code belongs in `composeApp/src/commonMain` so an iOS target can be added later without moving feature code.

## Requirements

- JDK 17
- Android SDK Platform 36

## Build

```sh
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:lintDebug
./gradlew :composeApp:testDebugUnitTest
```

## Architecture

- `core`: design system, networking, storage, and shared infrastructure.
- `data`: API implementations and repositories.
- `domain`: immutable models and focused use cases.
- `feature-*`: presentation, state, and UI for one product area.
- `composeApp/src/commonMain`: shared application code.
- `composeApp/src/androidMain`: Android entry point and platform integrations.

The initial implementation wires Omicron's exact web token set through `OmicronTheme`, RikkaUI foundation, and the Lucide RikkaIcons pack. RikkaUI primitive source is owned under `core/designsystem/rikkaui` and is managed by `rikkaui`.

## Instance Connection

The planned first-launch flow defaults to `https://omicron.blog`. It will validate an instance with root `GET /healthz` or `GET /version`, then retrieve `GET /api/instance` before saving the normalized HTTPS origin. It is not implemented in this bootstrap.

See [roadmap.md](roadmap.md) for milestones, confirmed API behavior, and known backend gaps.

## License

GPL-3.0-only. See [LICENSE](LICENSE).
