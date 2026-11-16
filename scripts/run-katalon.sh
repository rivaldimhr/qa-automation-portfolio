#!/usr/bin/env bash
# Jalankan project Katalon (folder katalon/) dengan Katalon Runtime Engine di Docker.
# Butuh: Docker, variabel KATALON_API_KEY, KINGSHANDs di localhost:8000 dan Restful-Booker di localhost:3001.
#
# Contoh:
#   scripts/run-katalon.sh "-testSuiteCollectionPath=Test Suites/Regression Lengkap"
#   scripts/run-katalon.sh "-testSuitePath=Test Suites/Known Bugs"
set -euo pipefail
cd "$(dirname "$0")/.."
docker run --rm --network host -v "$PWD/katalon":/tmp/project katalonstudio/katalon \
  katalonc.sh -projectPath=/tmp/project -executionProfile=local "-browserType=Chrome (headless)" \
  -retry=0 -statusDelay=15 -apiKey="$KATALON_API_KEY" --config -webui.autoUpdateDrivers=true "$@"
