# ADR-0001: Android-First Product Boundary

**Status:** Accepted

## Context

Omicron needs an Android client now, while the product may later support other native platforms. Duplicating product behavior in Android-only packages would make a future target expensive and encourage framework types to spread through the application.

## Decision

Android is the only active target. Product models, repositories, feature state, and Compose UI live in `composeApp/src/commonMain`. Android framework entry points, Ktor engines, platform storage, and other Android integrations live in `composeApp/src/androidMain`.

The project remains one Gradle module until build time, ownership, or dependency isolation justifies a measured split.

## Consequences

- Android delivery can proceed without maintaining an unused second target.
- Product code must avoid Android framework APIs and receive platform services through explicit contracts.
- Adding another target requires a new ADR and its platform implementations; it does not permit moving existing product code into platform packages.

## References

- R0 and R1 in `roadmap.md`
- `docs/architecture.md`
