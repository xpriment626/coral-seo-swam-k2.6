# coral-seo-swam-k2.6

A CoralOS multi-agent SEO sandbox built on the official Koog/Kotlin template, configured to probe Kimi K2.6's multi-agent coordination and tool-selection behaviour.

It ships with three specialized agents:

- `keyword-researcher` for search intent, Exa-driven web research, and keyword clustering
- `content-strategist` for briefs, outlines, entity coverage, and internal-link planning
- `draft-polisher` for drafting, rewriting, and on-page SEO cleanup

## Why Koog/Kotlin

Coral's own quickstart currently positions Koog/Kotlin as the highest-maturity template path, with Rust as medium-high and LangChain/Python as low. This repo follows that recommendation and keeps the agent logic close to Coral's native coordination model.

## Default model path

The scaffold is preconfigured for OpenRouter with `moonshotai/kimi-k2.6`:

- provider: `OPENROUTER`
- base URL: *(client default — no override)*
- model: `moonshotai/kimi-k2.6`
- auth: your OpenRouter token via `MODEL_API_KEY`

The Koog template was patched so `MODEL_ID` can be any OpenAI-compatible model string instead of only a baked-in enum.

## Tool surface

All three agents connect to two MCP servers and see a merged tool registry:

- **Coral MCP** (session-local): thread creation, mentions, inter-agent messaging, wait_for_mention, etc.
- **Exa MCP** (`https://mcp.exa.ai/mcp`): `web_search_exa` and `web_fetch_exa` for live research.

Tool selection is intentionally not scoped by role — K2.6 chooses what to reach for. Each agent requires both `MODEL_API_KEY` (OpenRouter) and `EXA_API_KEY` in its session options.

## Repo layout

- `agents/keyword-researcher`
- `agents/content-strategist`
- `agents/draft-polisher`
- `registry.toml` — single config entry point; launch via `CONFIG_FILE_PATH=./registry.toml npx coralos-dev@latest server start`
- `sandbox.http` — REST Client file that creates a session, seeds a puppet thread, and sends the user prompt in one chain
- `prewarm.sh` — compiles every agent once so Coral sessions don't race the cold Kotlin build
- `.env.example` — required keys (`OPENROUTER_API_KEY`, `EXA_API_KEY`)
- `sessions/seo-swarm.session.json` — original scaffolder session payload, kept for reference (still on HF defaults, not the active flow)
- `docs/architecture.md` — implementation notes and coordination model

## Running the sandbox

1. `cp .env.example .env` and fill in `OPENROUTER_API_KEY` and `EXA_API_KEY`
2. `./prewarm.sh` to pre-compile agents
3. `CONFIG_FILE_PATH=./registry.toml npx coralos-dev@latest server start`
4. Open the console at `http://localhost:5555/ui/console` (auth key: `dev`)
5. Open `sandbox.http` in VS Code with the REST Client extension, click Send on `createSession` → reload the console → Send on `createThread` → Send on the seed message
6. Watch the thread view as agents coordinate

## Session shape

The sandbox session uses one shared group for all three agents plus the built-in `puppet` debug agent, so they're mutually visible at session start and you can drive thread activity via puppet's HTTP API.

## Notes

- The repo tracks Kotlin/Koog changes against Koog `0.6.4`. Earlier scaffolder code used `LLMCapability.Tool` / `Schema.StrictJSON` / `contextLength: Int` which don't exist in 0.6.x — the correct names (`Tools`, `Schema.JSON.Standard`, `Long?` context, `Completion` required) are in `agents/*/src/main/kotlin/**/util/models.kt`.
- Coral Console doesn't auto-poll for new sessions. After firing `createSession` you must reload the page for the session dropdown to pick it up.
