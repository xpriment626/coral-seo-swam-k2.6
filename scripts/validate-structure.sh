#!/usr/bin/env bash
set -euo pipefail
for dir in agents/keyword-researcher agents/content-strategist agents/draft-polisher; do
  test -f "$dir/coral-agent.toml"
  test -f "$dir/build.gradle.kts"
  test -f "$dir/settings.gradle.kts"
  test -f "$dir/gradlew"
done
test -f registry.toml
test -f sessions/seo-swarm.session.json
echo "structure looks good"
