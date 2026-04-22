# architecture

## agent roles

### keyword-researcher
- owns search intent analysis
- clusters primary and long-tail keywords
- extracts SERP patterns and competitor signals
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

## model wiring

The agents default to Hugging Face's OpenAI-compatible router endpoint and the Novita-routed `moonshotai/Kimi-K2.6:novita` identifier. This keeps model experiments easy later because the SEO logic is role-driven while the model is an option.
