import { beforeEach, expect, test, vi } from "vitest";

const { config, deleteAll, get, remove, set } = vi.hoisted(() => ({
  config: { SESSION_SECRET: "old-secret" },
  deleteAll: vi.fn<() => Promise<void>>(),
  get: vi.fn<(key: string) => Promise<unknown>>(),
  remove: vi.fn<(key: string) => Promise<void>>(),
  set: vi.fn<(key: string, value: unknown) => Promise<void>>(),
}));

vi.mock("@/config.ts", () => ({ config }));
vi.mock("@/db/repositories/instanceSettings.ts", () => ({ get, remove, set }));
vi.mock("@/db/repositories/jwks.ts", () => ({ deleteAll }));

import { beginRollover, completeRollover } from "@/services/jwtKeys.ts";

beforeEach(() => {
  config.SESSION_SECRET = "old-secret";
  deleteAll.mockReset();
  get.mockReset();
  remove.mockReset();
  set.mockReset();
  deleteAll.mockResolvedValue();
  remove.mockResolvedValue();
  set.mockResolvedValue();
});

test("holds JWT issuance until the process starts with the rotated session secret", async () => {
  await beginRollover("new-secret");
  get.mockResolvedValue(set.mock.calls[0]?.[1]);

  await expect(completeRollover()).resolves.toBe(false);
  expect(deleteAll).not.toHaveBeenCalled();
  expect(remove).not.toHaveBeenCalled();
});

test("replaces signing keys after the process starts with the rotated session secret", async () => {
  await beginRollover("new-secret");
  get.mockResolvedValue(set.mock.calls[0]?.[1]);
  config.SESSION_SECRET = "new-secret";

  await expect(completeRollover()).resolves.toBe(true);
  expect(deleteAll).toHaveBeenCalledOnce();
  expect(remove).toHaveBeenCalledWith("auth.jwtKeySecretFingerprint");
  expect(deleteAll.mock.invocationCallOrder[0]).toBeLessThan(remove.mock.invocationCallOrder[0]);
});
