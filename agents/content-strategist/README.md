# content-strategist

Koog-based CoralOS agent for the `content-strategist` role in the `coral-seo-swam-k2.6` system.

## Role

Synthesizes research into briefs, outlines, audience angles, and internal linking guidance for the writer.

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
