# ADR-0004: JWT API Authentication

**Status:** Accepted

## Context

Android needs an authentication transport for normal API calls without relying on a browser cookie jar. Adding a separate mobile API would duplicate the product contract and make every future client capability platform-specific.

## Decision

Better Auth remains responsible for sign-in, sign-out, session restoration, and token minting. Its JWT plugin exposes the standard `GET /api/auth/token` and `GET /api/auth/jwks` endpoints. The normal API verifies these signed JWTs in its existing session middleware, then loads the current user row before authorizing a request.

Tokens contain only standard JWT claims and expire after 15 minutes. Android keeps a token in memory and sends it as `Authorization: Bearer`; its securely stored, origin-partitioned Better Auth cookie only restores the session and mints a replacement token. Browser clients continue using cookie sessions.

## Consequences

- All non-cookie clients use the same `/api` routes and authorization rules as web clients.
- Roles, suspension, and deletion are checked from the current user row on every JWT-authenticated request.
- Session revocation prevents future token minting, but an issued JWT can remain valid for up to 15 minutes.
- JWT signing keys are stored by Better Auth and encrypted with the session secret. Secret rotation holds JWT endpoints until restart, then replaces the signing keys and invalidates existing JWTs.
- OAuth and device authentication remain out of scope.

## References

- R1 and API inventory in `roadmap.md`
- `../../../backend/src/auth/auth.ts`
- `../../../backend/src/routes/middleware.ts`
- https://better-auth.com/docs/plugins/jwt
