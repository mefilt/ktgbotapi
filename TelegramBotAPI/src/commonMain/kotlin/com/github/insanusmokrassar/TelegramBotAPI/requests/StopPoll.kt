package com.github.insanusmokrassar.TelegramBotAPI.requests

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.types.MessageAction
import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.types.ReplyMarkup
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.SimpleRequest
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.InlineKeyboardMarkup
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.Chat
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.Message
import com.github.insanusmokrassar.TelegramBotAPI.types.polls.Poll
import kotlinx.serialization.*

@Serializable
data class StopPoll(
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier,
    @SerialName(messageIdField)
    override val messageId: MessageIdentifier,
    @SerialName(replyMarkupField)
    override val replyMarkup: InlineKeyboardMarkup? = null
) : MessageAction, SimpleRequest<Poll>, ReplyMarkup {
    override fun method(): String = "stopPoll"
    override val resultDeserializer: DeserializationStrategy<Poll>
        get() = Poll.serializer()
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}
