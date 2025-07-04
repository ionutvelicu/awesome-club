import "dotenv/config";
import pg from "pg";
import type { ProductDto } from "../generated/api-client";

const { Pool } = pg;

const pool = new Pool({
  connectionString: process.env.DATABASE_URL,
});

export async function fetchProduct(url: String): Promise<ProductDto> {
  const res = await pool.query("SELECT * FROM product WHERE url = $1", [url]);
  if (res.rows.length === 0) {
    throw new Error(`Product not found for URL: ${url}`);
  }

  return res.rows[0] as ProductDto;
}
