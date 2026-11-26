const { execFileSync } = require("node:child_process");
const { createHash } = require("node:crypto");
const { existsSync, readFileSync, writeFileSync } = require("node:fs");
const { join } = require("node:path");

const packageFiles = ["package.json", "package-lock.json", ".npmrc"];
const dependencyHash = createHash("sha256")
  .update(packageFiles.map((file) => readFileSync(file)).join("\n"))
  .digest("hex");
const hashFile = join("node_modules", ".vitalog-dependency-hash");
const installedHash = existsSync(hashFile)
  ? readFileSync(hashFile, "utf8").trim()
  : "";
const reactScripts = join("node_modules", ".bin", "react-scripts");

if (!existsSync(reactScripts) || installedHash !== dependencyHash) {
  console.log("Installing frontend dependencies for the current package files...");
  execFileSync("npm", ["ci", "--no-audit", "--no-fund"], { stdio: "inherit" });
  writeFileSync(hashFile, `${dependencyHash}\n`);
} else {
  console.log("Frontend dependencies are already up to date.");
}
