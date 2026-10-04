# AGENTS.md

## Scope

This repository is the official Omicron mobile client. Android is the only active target. Keep product and shared code in `composeApp/src/commonMain`; place Android-only integrations in `composeApp/src/androidMain`. Do not add an iOS target or iOS implementation unless the task explicitly asks for it.

The Omicron server and web client live in `../backend` and `../frontend`. Read the repository-root `AGENTS.md` before changing behavior that depends on Omicron conventions or APIs.

## Source Of Truth

- Delivery plan and release gates: `roadmap.md`.
- Mobile architecture and dependency direction: `docs/architecture.md`.
- Development, verification, and review procedure: `docs/development.md`.
- Durable technical decisions: `docs/adr/`.
- Web design tokens: `../frontend/src/app.css`.
- Web screens and interaction behavior: `../frontend/src/routes` and `../frontend/src/lib/components`.
- API types: `../frontend/src/lib/api/contract.ts`.
- API behavior: `../backend/src/routes`, serializers, and services.
- Live visual reference: `https://omicron.blog` at a 390px viewport.

Do not invent endpoints, request fields, response fields, pagination, or screens. Record an unfulfilled mobile requirement in `roadmap.md` as an API gap instead of changing the backend from this repository.

When a mobile requirement exposes a general server capability, its backend implementation belongs in `../backend` and must serve every client without mobile-specific routes, fields, or transports.

## Planning

- Treat `roadmap.md` as the delivery order. Every product change must map to one release, exit criterion, or API-gap entry before implementation.
- Keep plans durable: record product state, committed decisions, dependencies, risks, and exit criteria. Do not use session notes, temporary ownership, or conversational context as repository documentation.
- Update the roadmap when scope, API findings, delivery status, or release gates change. Use `Planned`, `In progress`, `Blocked`, or `Done` consistently.
- Add an ADR from `docs/adr/README.md` when a decision affects public behavior, architecture boundaries, persistence, authentication, navigation, offline behavior, dependencies, or future platform support.
- Keep changes within the selected roadmap scope. Capture adjacent work as a planned follow-up rather than silently expanding a feature.

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

- Use root `GET /healthz` or `GET /version` when an instance publishes them; bootstrap metadata is `GET /api/instance`. Do not reject an otherwise valid instance solely because the currently documented deployment gap makes the root routes unavailable.
- The normal app API is rooted at `/api`. Better Auth uses `/api/auth/*`.
- Authenticated app endpoints accept Better Auth JWTs through `Authorization: Bearer`. Mint a 15-minute token through authenticated `GET /api/auth/token`; public signing keys are at `GET /api/auth/jwks`.
- Persist Better Auth session cookies securely per instance origin only to restore a session and mint replacement tokens. Keep JWTs in memory and never send either credential to another origin. No OAuth or device-auth transport is confirmed.
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
- Before implementation, read the applicable roadmap release, architecture guidance, and source-of-truth server or web code.
- Before opening a pull request, update planning artifacts and docs affected by the change, then run the relevant Gradle build, lint, and test tasks.
- The CI workflow is the minimum merge gate. Do not merge failed or skipped quality checks without documenting the reason and follow-up in the pull request.
