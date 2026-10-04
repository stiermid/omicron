import { db } from "@/db/client.ts";
import { jwks } from "@/db/schema.ts";

export async function deleteAll() {
  await db.delete(jwks);
}
