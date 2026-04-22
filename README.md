# coral-seo-swam-k2.6

A CoralOS multi-agent SEO scaffold built on the official Koog/Kotlin template.

It ships with three specialized agents:

- `keyword-researcher` for search intent, SERP patterning, and keyword clustering
- `content-strategist` for briefs, outlines, entity coverage, and internal-link planning
- `draft-polisher` for drafting, rewriting, and on-page SEO cleanup

## Why Koog/Kotlin

Coral's own quickstart currently positions Koog/Kotlin as the highest-maturity template path, with Rust as medium-high and LangChain/Python as low. This repo follows that recommendation and keeps the agent logic close to Coral's native coordination model.

## Default model path

This scaffold is preconfigured for Hugging Face Inference Providers with Novita routing:

- provider: `OPENAI`
- base URL: `https://router.huggingface.co/v1`
- model: `moonshotai/Kimi-K2.6:novita`
- auth: your Hugging Face token via `MODEL_API_KEY`

The Koog template was patched so `MODEL_ID` can be any OpenAI-compatible model string instead of only a baked-in enum.

## Repo layout

- `agents/keyword-researcher`
- `agents/content-strategist`
- `agents/draft-polisher`
- `registry.toml` for loading all local agents into Coral Server
- `sessions/seo-swarm.session.json` example session payload
- `docs/architecture.md` implementation notes and coordination model

## Running with Coral Server

1. Start Coral Server
2. Point the server registry at this repo's `agents/*`
3. Create a session using `sessions/seo-swarm.session.json`
4. Replace `YOUR_HF_TOKEN` in the session payload before use

A local registry example is included in `registry.toml`.

## Session shape

The default session uses one shared group for all three agents so they can immediately see and message each other over Coral's thread system.

## Notes

This environment did not have Java installed, so the repo was scaffolded carefully but not compiled in-place here. Once JDK 24 is available, each agent should be validated with `./gradlew test` and `./gradlew run`.
