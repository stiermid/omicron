# Android Roadmap

## Status

The Android-first Kotlin Multiplatform bootstrap is in place. The active target is Android only. `commonMain` is reserved for shared product code so iOS can be introduced later without relocating features.

## Confirmed Product Surfaces

- Instance connection and account authentication.
- Home feed: signed-in For you, Local, and Global tabs; guest Global feed and writing prompt.
- Post detail: author, metadata, cover, rendered content, tags, likes, recommendations, saving, comments, related posts, and reporting.
- Local and remote profiles: posts, recommendations, about, followers, following, and follow, mute, or block controls where available.
- Search: articles, tags, and people; tag and author article filters.
- Tags, trending posts, suggested people, and topics.
- Compose, drafts, publishing, scheduling, post management, and dashboard are web surfaces. Compose comes after reading and social features; mobile may use Markdown only if converted to accepted HTML.
- Settings: instance, appearance, about, and sign out are v1. More web settings follow after core reader parity.

## Token Mapping

`composeApp/src/commonMain/kotlin/org/omicron/mobile/core/designsystem/OmicronTheme.kt` ports the exact `app.css` light and dark color, radius, shadow, and font-stack tokens.

- Rikka `background` maps to web `background`; `surface` maps to `background-alt`.
- Rikka primary maps to `dark`; its foreground maps to `background`.
- Rikka secondary maps to `muted`; primary tint maps to `accent`; destructive maps directly.
- Rikka borders map to `border-card`. `border-input`, `foreground-alt`, scrollbar, all web radii, and all web shadows remain available as Omicron-specific tokens.
- Web fonts are represented by their exact stacks. Android font resources for Inter, Source Sans 3, and Twemoji are a design-parity follow-up before visual sign-off.

## Module Layout

Begin with one `composeApp` KMP module to keep the bootstrap small. Keep these packages isolated so they can become Gradle modules only when build time or ownership requires it:

- `core.designsystem`, `core.network`, `core.storage`.
- `data.api`, `data.repository`, `data.cache`.
- `domain.model`, `domain.usecase`.
- `feature.connect`, `feature.auth`, `feature.feed`, `feature.post`, `feature.profile`, `feature.search`, `feature.compose`, `feature.settings`.

## API Inventory

| Need | Confirmed endpoint |
| --- | --- |
| Instance validation | `GET /healthz` returns `{status:"ok"}`; `GET /version` returns `{name,version,federation}` |
| Instance metadata | `GET /api/instance` |
| Session | Better Auth under `/api/auth/*`; `GET /api/auth/get-session`; `POST /api/auth/sign-out` |
| Global and Local posts | `GET /api/posts`, with `scope=local`, opaque `cursor`, and language filters |
| Following feed | `GET /api/feed?cursor=...` |
| Post and comments | `GET /api/posts/:id`, `GET /api/posts/by/:username/:slug`, `GET /api/posts/:id/comments?cursor=...` |
| Post interactions | `POST` or `DELETE` `/api/posts/:id/like` and `/recommend`; comments under `/api/posts/:id/comments` |
| Profiles | `/api/users/:username`, `/posts`, `/recommendations`, `/followers`, `/following`; remote equivalents under `/api/remote/users/:handle` when federation is enabled |
| Search and tags | `/api/search`, `/api/tags`, `/api/tags/:slug`, `/api/tags/:slug/posts` |
| Discovery | `/api/posts/trending`, `/api/posts/:id/related`, `/api/users/suggested` |
| Publishing | `POST` and `PATCH /api/posts`; HTML content is required; raw image upload is `POST /api/uploads` |

All regular pages use opaque cursor/keyset pagination and return `{items,nextCursor}`. Never calculate, decode, or replace a cursor. Feed cursors are opaque merged-stream state.

## Milestones

### 1. Project And Design System

- Initialize the Android KMP shell, GPLv3 license, Gradle wrapper, RikkaUI CLI config, RikkaUI foundation, and RikkaIcons Lucide pack.
- Port Omicron web tokens exactly and protect light/dark aliases with unit tests.
- Add Android release shrinking, edge-to-edge entry point, Compose resources, and a baseline build/lint/test workflow.

### 2. Instance Connection And Auth

- Add Ktor, kotlinx.serialization, Koin, type-safe Compose Navigation, and secure per-instance preferences after checking their current compatible releases.
- Normalize and validate a user-entered HTTPS instance origin, defaulting to `https://omicron.blog`.
- Query instance metadata and retain per-instance configuration.
- Implement Better Auth email and username sign-in, registration, session restoration, secure cookie storage, verification handoff, and sign out.
- Add loading, invalid-instance, authentication, verification-required, and offline states.

### 3. Feed

- Add Coil 3 and the local cache implementation after checking their current compatible releases.
- Build Global, Local, and authenticated For you timelines from actual API scopes.
- Add reusable opaque-cursor pagination, pull to refresh, skeletons, stable lazy-list keys, image sizing, and latest-feed cache.
- Add trending, suggested people, and topics as native discovery surfaces.

### 4. Post Reader And Interactions

- Render sanitized article HTML in Compose: headings, paragraphs, marks, links, lists, quotes, code, images, figures, tables, details, definition lists, and MathML where feasible.
- Add post caching, comments, related posts, Custom Tabs for external links, and optimistic like/recommend/save operations with rollback.
- Document any unsupported sanitized HTML before considering a WebView fallback.

### 5. Profiles, Search, And Tags

- Implement local and remote actor profiles, posts, recommendations, follower lists, follow state, and optimistic follow changes.
- Implement search scopes and filters, tag discovery, tag pages, and tag following.

### 6. Compose And Publishing

- Build this last.
- Use a mobile Markdown editor only if it converts losslessly to accepted sanitized HTML. Do not depend on opaque Tiptap JSON as a portable authoring format.
- Implement image uploads, drafts, publishing, scheduling, and failure recovery from confirmed endpoints.

### 7. Settings And Release Readiness

- Add instance management, system/light/dark selection, about, and sign out.
- Complete caches and offline reading, baseline profile, compiler metrics review, R8 validation, accessibility pass, and startup profiling.
- Finish README setup instructions, known issues, and an updated API-gap list.

## API Gaps And Constraints

- No confirmed bearer, JWT, OAuth, or device-auth flow. Native authentication must retain Better Auth session cookies securely per instance.
- Email verification links target the web verification route. No Android App Link or deep-link flow is confirmed.
- No push, WebSocket, SSE, or device-token API exists. Notifications are polling only.
- No public versioned schema or OpenAPI contract exists; `contract.ts` is compile-time frontend checking only.
- The live `omicron.blog` deployment returns 404 for `/healthz` and `/version` despite the backend defining those root routes. The mobile bootstrap therefore validates by retrieving the required public `/api/instance` metadata.
- Feed responses contain full HTML and editor JSON, with no mobile projection, fields selector, or page-size control.
- There is no documented portable rich-text authoring payload. The server requires HTML; `contentJson` is opaque web-editor data.
- Followers, following, and search have no cursor pagination. Search results are capped server-side.
- There is no delta-sync or offline-sync endpoint.
- Remote profile routes return 404 when federation is disabled.

## Known Issues

- Instance connection is available; Better Auth and reader features are not yet implemented.
- Android font resources have not been bundled, so the exact web typefaces are not yet rendered.
- No cache, secure cookie storage, authentication flow, or navigation graph exists yet.
