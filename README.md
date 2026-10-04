# Omicron for Android

The Android client for [Omicron](https://github.com/the-jk-labs/omicron), a federated and self-hostable ActivityPub blogging platform.

This repository contains the Android-first Kotlin Multiplatform client. Android is the only active target; shared application code belongs in `composeApp/src/commonMain` so a future target can be introduced without relocating product code.

## Requirements

- JDK 17
- Android SDK Platform 36

## Build

```sh
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:lintDebug
./gradlew :composeApp:testDebugUnitTest
```

## Development

- [Development guide](docs/development.md): setup, workflow, verification, and pull requests.
- [Architecture](docs/architecture.md): package boundaries, state, networking, persistence, and UI conventions.
- [Roadmap](roadmap.md): ordered releases, exit criteria, and API constraints.
- [Decision records](docs/adr/README.md): durable technical decisions and their rationale.

## Current Capability

- Connect to an HTTPS Omicron instance, defaulting to `https://omicron.blog`.
- Retrieve and retain public instance metadata from `GET /api/instance`.
- Present loading, invalid-address, unreachable, and retry states for instance connection.

Authentication, feeds, reading, social interactions, publishing, settings, and offline cache work are planned releases. See [roadmap.md](roadmap.md).

## Repository Rules

`AGENTS.md` is required reading for contributors and coding agents. It defines the source of truth, design system, architecture, API rules, and quality bar for this client.

## License

AGPL-3.0-or-later. See [LICENSE](LICENSE).
