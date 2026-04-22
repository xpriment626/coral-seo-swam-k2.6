# local e2e notes

This repo was validated against a local Coral Server started from the quickstart path.

## start the server

```bash
npx coralos-dev@latest server start -- \
  --auth.keys=dev \
  --registry.local-agents="/absolute/path/to/coral-seo-swam-k2.6/agents/*" \
  --registry.include-coral-home-agents=false \
  --registry.include-debug-agents=false
```

Expected startup signal in logs:
- `agent added: local/keyword-researcher:0.1.0`
- `agent added: local/content-strategist:0.1.0`
- `agent added: local/draft-polisher:0.1.0`
- `Responding at http://0.0.0.0:5555`

## create a real session

Use `sessions/seo-swarm.session.json` after replacing `YOUR_HF_TOKEN`.

```bash
curl -H 'Authorization: Bearer dev' \
  -H 'Content-Type: application/json' \
  --data @sessions/seo-swarm.session.json \
  http://localhost:5555/api/v1/local/session
```

## create a no-op bootstrap session

Use `sessions/seo-swarm.e2e-noop.session.json` to validate session creation and agent pickup without running a real model loop.

```bash
curl -H 'Authorization: Bearer dev' \
  -H 'Content-Type: application/json' \
  --data @sessions/seo-swarm.e2e-noop.session.json \
  http://localhost:5555/api/v1/local/session
```

## schema gotcha that was validated

Current Coral Server expects each agent id to use:

```json
{
  "name": "keyword-researcher",
  "version": "0.1.0",
  "registrySourceId": { "type": "local" }
}
```

not the older `source: "local"` shape.
