const fs = require("fs");
const path = require("path");
const { spawnSync } = require("child_process");

const browserCandidates = [
  process.env.CHROME_BIN,
  "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
  "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
  "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe",
  "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe",
  "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
  "/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge",
  "/usr/bin/google-chrome",
  "/usr/bin/google-chrome-stable",
  "/usr/bin/chromium",
  "/usr/bin/chromium-browser",
  "/usr/bin/microsoft-edge",
].filter(Boolean);

function findBrowserBinary() {
  return browserCandidates.find((candidate) => fs.existsSync(candidate));
}

const browserBinary = findBrowserBinary();

if (!browserBinary) {
  console.error(
    "No supported Chromium-based browser was found. Install Google Chrome or Microsoft Edge, or set CHROME_BIN."
  );
  process.exit(1);
}

const angularCliScript = path.join(
  __dirname,
  "..",
  "node_modules",
  "@angular",
  "cli",
  "bin",
  "ng.js"
);

const result = spawnSync(
  process.execPath,
  [angularCliScript, "test", "--watch=false", "--browsers=ChromeHeadless"],
  {
    stdio: "inherit",
    env: {
      ...process.env,
      CHROME_BIN: browserBinary,
    },
  }
);

if (result.error) {
  console.error(result.error.message);
  process.exit(1);
}

process.exit(result.status ?? 1);
