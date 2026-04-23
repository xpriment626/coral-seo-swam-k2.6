# keyword-researcher

Koog-based CoralOS agent for the `keyword-researcher` role in the `coral-seo-swam-k2.6` system.

## Role

Discovers search intent, clusters keywords, surfaces competitive landscape via Exa web search, and hands structured research to the strategist.

## Default model wiring

- `MODEL_PROVIDER=OPENROUTER`
- `MODEL_PROVIDER_URL_OVERRIDE=` *(empty — client uses OpenRouter's own base URL)*
- `MODEL_ID=moonshotai/kimi-k2.6`
- `MODEL_API_KEY=<your OpenRouter token>`
- `EXA_API_KEY=<your Exa token>` *(required — drives the hosted MCP research tools)*

## Tool surface

- Coral MCP: create/close thread, add/remove participant, send_message, wait_for_message, wait_for_mention, wait_for_agent
- Exa MCP (via `https://mcp.exa.ai/mcp`): `web_search_exa`, `web_fetch_exa`

## Run

```bash
./gradlew run
```
