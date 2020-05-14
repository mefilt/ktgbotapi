package com.github.insanusmokrassar.TelegramBotAPI.bot

import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.Request
import io.ktor.utils.io.core.Closeable

/**
 * Interface for making requests to Telegram Bot API
 *
 * @see Request
 * @see com.github.insanusmokrassar.TelegramBotAPI.bot.Ktor.KtorRequestsExecutor
 */
interface RequestsExecutor : Closeable {
    /**
     * Unsafe execution of incoming [request]. Can throw almost any exception. So, it is better to use
     * something like [com.github.insanusmokrassar.TelegramBotAPI.utils.extensions.executeAsync] or
     * [com.github.insanusmokrassar.TelegramBotAPI.utils.extensions.executeUnsafe]
     *
     * @throws Exception
     */
    suspend fun <T : Any> execute(request: Request<T>): T
}