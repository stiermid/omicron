import { config } from "@/config.ts";
import * as settings from "@/db/repositories/instanceSettings.ts";
import * as jwks from "@/db/repositories/jwks.ts";

const SECRET_FINGERPRINT_KEY = "auth.jwtKeySecretFingerprint";

async function fingerprint(secret: string) {
  const bytes = new Uint8Array(await crypto.subtle.digest("SHA-256", new TextEncoder().encode(secret)));
  return Array.from(bytes, (byte) => byte.toString(16).padStart(2, "0")).join("");
}

export async function beginRollover(nextSecret: string) {
  await settings.set(SECRET_FINGERPRINT_KEY, await fingerprint(nextSecret));
}

export async function completeRollover() {
  const expected = await settings.get<string>(SECRET_FINGERPRINT_KEY);
  if (!expected) return true;
  if (expected !== (await fingerprint(config.SESSION_SECRET))) return false;
  await jwks.deleteAll();
  await settings.remove(SECRET_FINGERPRINT_KEY);
  return true;
}
