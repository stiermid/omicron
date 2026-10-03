# AGENTS.md

## Scope

This repository is the official Omicron mobile client. Android is the only active target. Keep product and shared code in `composeApp/src/commonMain`; place Android-only integrations in `composeApp/src/androidMain`. Do not add an iOS target or iOS implementation unless the task explicitly asks for it.

The Omicron server and web client live at `../omicron`. Read `../omicron/AGENTS.md`, then its linked `CLAUDE.md`, before changing behavior that depends on Omicron conventions or APIs.

## Source Of Truth

- Web design tokens: `../omicron/apps/frontend/src/app.css`.
- Web screens and interaction behavior: `../omicron/apps/frontend/src/routes` and `../omicron/apps/frontend/src/lib/components`.
- API types: `../omicron/apps/frontend/src/lib/api/contract.ts`.
- API behavior: `../omicron/apps/backend/src/routes`, serializers, and services.
- Live visual reference: `https://omicron.blog` at a 390px viewport.

Do not invent endpoints, request fields, response fields, pagination, or screens. Record an unfulfilled mobile requirement in `roadmap.md` as an API gap instead of changing the backend from this repository.

## Architecture

Use a layered package layout under `org.omicron.mobile`:

- `core`: design system, network, storage, and shared utilities.
- `data`: API clients, DTOs, local cache, and repository implementations.
- `domain`: immutable models and use cases only when they remove meaningful duplication.
- `feature.<name>`: state, ViewModel, route, and composables for one feature.

Use StateFlow and unidirectional state. Keep shared models immutable. Give lazy-list items stable keys and content types. Keep blocking work off the main thread.

Use type-safe Compose Navigation routes. Introduce Ktor with kotlinx.serialization, Koin, Coil 3, and multiplatform preferences or storage only with the feature that uses each dependency; check current stable versions before pinning them in the version catalog.

## UI

- Use RikkaUI for every primitive it provides. Do not add Material3.
- Run `rikkaui add <component>` before using a new RikkaUI primitive. The configured destination and package are in `rikka.json`.
- Use RikkaIcons with the Lucide pack only.
- Use `OmicronTheme` and `OmicronTheme.colors`, `radii`, and `shadows`. Do not add colors, radii, shadows, or font substitutions outside `core/designsystem/OmicronTheme.kt`.
- All visible strings belong in `composeApp/src/commonMain/composeResources/values/strings.xml`.
- Preserve light and dark appearance, edge-to-edge drawing, dynamic font scaling, and 48dp minimum touch targets.
- Use native Compose article rendering. Do not introduce a WebView unless a documented content construct makes it unavoidable.

The exact web font stacks are tokenized, but their Android font assets are not yet bundled. Do not silently replace them with a new visual typeface; add licensed Inter and Source Sans 3 assets as part of the design-parity milestone.

## Network And Auth

- Instance validation is root `GET /healthz` or `GET /version`; bootstrap metadata is `GET /api/instance`.
- The normal app API is rooted at `/api`. Better Auth uses `/api/auth/*`.
- Authenticated app endpoints use a Better Auth httpOnly session cookie. There is no confirmed bearer, JWT, OAuth, or device-auth transport. Persist session cookies securely per instance and send them only to that instance origin.
- Core pages use opaque cursor/keyset pagination: preserve `nextCursor` unchanged and never use offsets. `/api/feed` has its own opaque merged-feed cursor.
- Resolve root-relative media URLs against the current instance origin.
- Posts expose sanitized `contentHtml`; comments are plain text. The only structured editor payload is opaque web Tiptap JSON.

## Quality

- Write clean Kotlin with small single-purpose composables and no code comments.
- Add unit coverage for repository, paginator, and ViewModel behavior with fake Ktor engines and fixtures as those layers are introduced.
- Add Compose UI tests for critical user journeys.
- Every screen needs loading, empty, error, offline, and retry states.
- Run the relevant Gradle build, lint, and test tasks before reporting completion. State exactly what ran and what could not be verified.

## Workflow

- Work on `feat/`, `fix/`, `docs/`, `refactor/`, or `chore/` branches, never directly on the default branch.
- Use focused conventional commits. Do not add AI attribution, co-author trailers, or generated-by notes.
- Keep `roadmap.md` current when scope, API findings, or milestone status changes.
