# keyword-researcher

Koog-based CoralOS agent for the `keyword-researcher` role in the `coral-seo-swam-k2.6` system.

## Role

Discovers search intent, clusters keywords, surfaces SERP patterns, and hands structured research to the strategist.

## Default model wiring

- `MODEL_PROVIDER=OPENAI`
- `MODEL_PROVIDER_URL_OVERRIDE=https://router.huggingface.co/v1`
- `MODEL_ID=moonshotai/Kimi-K2.6:novita`
- `MODEL_API_KEY=<your Hugging Face token>`

## Run

```bash
./gradlew run
```
