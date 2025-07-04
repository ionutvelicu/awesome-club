import { execSync } from "child_process";
import process from "process";
import fs from "fs";

const LCERROR = "\x1b[31m%s\x1b[0m";
const LCINFO = "\x1b[36m%s\x1b[0m";
const LCSUCCESS = "\x1b[32m%s\x1b[0m";

const Services = {
  api: { name: "api", url: "http://localhost:8080/api/v3/api-docs" },
};

async function start() {
  const service = process.argv[2];
  if (!service || !Services[service]) {
    console.error(
      LCERROR,
      "Run this command with a service name as a parameter.\nThe supported services are: api\nExample: npm run gen api.",
    );
    return;
  }

  try {
    const name = Services[service].name;
    const res = await fetch(Services[service].url);

    if (!res.ok) {
      throw new Error(
        `Failed to fetch OpenAPI spec: ${res.status} ${res.statusText}`,
      );
    }

    const data = await res.json();

    console.info(LCINFO, "Generating...");
    fs.writeFile(`${name}-api.json`, JSON.stringify(data), (err) => {
      if (err) return console.error(err);

      execSync(
        `openapi-generator-cli generate -g typescript-axios -o src/generated/${name}-client -i ${name}-api.json`,
        { stdio: "inherit" },
      );

      fs.unlinkSync(`${name}-api.json`);
      console.info(LCSUCCESS, "Done");
    });
  } catch (e) {
    console.error(LCERROR, e.message || e);
  }
}

start();
