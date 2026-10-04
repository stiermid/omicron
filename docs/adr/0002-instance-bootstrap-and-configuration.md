# ADR-0002: Instance Bootstrap And Configuration

**Status:** Accepted

## Context

Omicron is self-hostable, so a client must select an instance before it can read public metadata or establish a Better Auth session. The live `omicron.blog` deployment currently returns 404 for root health and version routes, although the server source defines them. It does return the public `GET /api/instance` payload.

## Decision

The client accepts a normalized HTTPS origin, retrieves `GET /api/instance`, and persists the returned public instance configuration only after a successful response. Root health or version routes may provide an additional deployment check when available, but instance metadata is the compatibility gate.

No credential is stored in this configuration. Better Auth cookie storage remains a separate R1 decision and must be partitioned by instance origin.

## Consequences

- Users can connect to the live Omicron deployment despite the documented root-route deployment gap.
- The selected origin and public identity are restored across launches.
- The client must not claim that a persisted instance configuration represents an authenticated session.
- Changes to server bootstrap behavior require an API-gap update and review of this ADR.

## References

- R1 and API gaps in `roadmap.md`
- `../omicron/apps/backend/src/routes/setup.ts`
- `../omicron/apps/backend/src/routes/health.ts`
