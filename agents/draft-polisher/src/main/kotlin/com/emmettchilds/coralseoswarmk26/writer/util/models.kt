package com.emmettchilds.coralseoswarmk26.writer.util

import ai.koog.prompt.executor.clients.anthropic.AnthropicClientSettings
import ai.koog.prompt.executor.clients.anthropic.AnthropicLLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIClientSettings
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openrouter.OpenRouterClientSettings
import ai.koog.prompt.executor.clients.openrouter.OpenRouterLLMClient
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.executor.model.PromptExecutor
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider

private const val DEFAULT_CONTEXT_WINDOW = 256000

fun findKoogModelByName(id: String, provider: ModelProvider): LLModel {
    val mappedProvider = when (provider) {
        ModelProvider.OPENAI -> LLMProvider.OpenAI
        ModelProvider.OPENROUTER -> LLMProvider.OpenRouter
        ModelProvider.ANTHROPIC -> LLMProvider.Anthropic
        ModelProvider.CORAL_LLM_PROXY -> LLMProvider.OpenRouter
    }

    return LLModel(
        provider = mappedProvider,
        id = id,
        capabilities = listOf(
            LLMCapability.Temperature,
            LLMCapability.Tool,
            LLMCapability.Schema.JSON,
            LLMCapability.Schema.StrictJSON,
        ),
        contextLength = DEFAULT_CONTEXT_WINDOW,
    )
}

enum class ModelProvider(val getExecutor: (urlOverride: String?, modelApiKey: String) -> PromptExecutor) {
    OPENAI({ urlOverride, modelApiKey ->
        SingleLLMPromptExecutor(
            OpenAILLMClient(
                apiKey = modelApiKey,
                settings = if (urlOverride == null) OpenAIClientSettings() else OpenAIClientSettings(baseUrl = urlOverride)
            )
        )
    }),
    OPENROUTER({ urlOverride, modelApiKey ->
        SingleLLMPromptExecutor(
            OpenRouterLLMClient(
                apiKey = modelApiKey,
                settings = if (urlOverride == null) OpenRouterClientSettings() else OpenRouterClientSettings(baseUrl = urlOverride)
            )
        )
    }),
    ANTHROPIC({ urlOverride, modelApiKey ->
        SingleLLMPromptExecutor(
            AnthropicLLMClient(
                apiKey = modelApiKey,
                settings = if (urlOverride == null) AnthropicClientSettings() else AnthropicClientSettings(baseUrl = urlOverride)
            )
        )
    }),
    CORAL_LLM_PROXY({ urlOverride, modelApiKey ->
        SingleLLMPromptExecutor(
            OpenRouterLLMClient(
                apiKey = modelApiKey,
                settings = if (urlOverride == null) OpenRouterClientSettings(baseUrl = System.getenv("CORAL_LLM_PROXY_BASE_URL")) else OpenRouterClientSettings(baseUrl = urlOverride)
            )
        )
    })
}
