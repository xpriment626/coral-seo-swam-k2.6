#!/usr/bin/env bash
# Compile each agent once so `./gradlew run` under a Coral session starts in seconds
# instead of racing the session timeout during a cold Kotlin build.
set -euo pipefail

cd "$(dirname "$0")"

for agent in agents/keyword-researcher agents/content-strategist agents/draft-polisher; do
  echo ">> warming $agent"
  (cd "$agent" && ./gradlew --quiet classes)
done

echo ">> done. Start the server with:"
echo "   CONFIG_FILE_PATH=./registry.toml npx coralos-dev@latest server start -- --auth.keys=dev"
