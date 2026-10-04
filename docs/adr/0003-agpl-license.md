# ADR-0003: AGPL-3.0-Or-Later Licensing

**Status:** Accepted

## Context

The Omicron server and web client are licensed under AGPL-3.0-or-later. Keeping the Android client under a different license would complicate a shared repository, product-wide licensing guidance, and future shared code.

## Decision

The Android client is licensed under AGPL-3.0-or-later, matching the main Omicron repository.

## Consequences

- The mobile client and main Omicron repository use the same copyleft license family and terms.
- Repository documentation and SPDX identifiers must use `AGPL-3.0-or-later`.
- Third-party assets and dependencies remain subject to their own licenses and must be reviewed before inclusion.

## References

- `LICENSE`
- `../omicron/LICENSE`
