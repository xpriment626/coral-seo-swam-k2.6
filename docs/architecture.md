# architecture

## agent roles

### keyword-researcher
- owns search intent analysis
- clusters primary and long-tail keywords
- gathers web evidence and competitor signals via Exa (`web_search_exa`, `web_fetch_exa`)
- hands research to the strategist in structured threads

### content-strategist
- converts research into a brief and outline
- aligns headings with intent and entity coverage
- recommends internal links and angle decisions
- hands a writing-ready brief to the writer

### draft-polisher
- drafts or revises content from the brief
- improves structure, scannability, and natural keyword use
- tightens copy and flags missing inputs back upstream

## coordination model

All three agents share one Coral group so they are mutually visible at session start. They are expected to:

- create threads per subtask or deliverable
- use mentions to wake the next specialist deterministically
- keep messages concise and artifact-oriented
- treat the session as the working memory boundary

## tool surface

All three agents see the same merged `ToolRegistry` at boot:

- **Coral MCP** (per-session URL from `CORAL_CONNECTION_URL`): thread creation, mention-based wakeups, inter-agent messaging
- **Exa MCP** (`https://mcp.exa.ai/mcp`, auth via `x-api-key` header): `web_search_exa`, `web_fetch_exa`

Role-based tool scoping is intentionally not enforced — the sandbox treats tool selection as part of the coordination signal under observation. Each agent receives `EXA_API_KEY` as a required session option and passes it as a default header when constructing the Exa MCP client.

## model wiring

The agents default to OpenRouter with `moonshotai/kimi-k2.6`. `MODEL_PROVIDER_URL_OVERRIDE` is left empty so the OpenRouter client uses its own base URL. The SEO role logic stays option-driven so the model is easy to swap later without touching Kotlin code.
