package dev.inmo.tgbotapi.extensions.utils.extensions

import dev.inmo.tgbotapi.abstracts.FromUser
import dev.inmo.tgbotapi.abstracts.WithUser
import dev.inmo.tgbotapi.extensions.utils.asUser
import dev.inmo.tgbotapi.types.InlineQueries.ChosenInlineResult.BaseChosenInlineResult
import dev.inmo.tgbotapi.types.InlineQueries.ChosenInlineResult.ChosenInlineResult
import dev.inmo.tgbotapi.types.InlineQueries.ChosenInlineResult.LocationChosenInlineResult
import dev.inmo.tgbotapi.types.InlineQueries.query.BaseInlineQuery
import dev.inmo.tgbotapi.types.InlineQueries.query.LocationInlineQuery
import dev.inmo.tgbotapi.types.chat.Chat
import dev.inmo.tgbotapi.types.chat.User
import dev.inmo.tgbotapi.types.queries.callback.*
import dev.inmo.tgbotapi.types.update.*
import dev.inmo.tgbotapi.types.update.abstracts.BaseMessageUpdate
import dev.inmo.tgbotapi.types.update.abstracts.Update
import dev.inmo.tgbotapi.utils.PreviewFeature

fun CallbackQuery.sourceChat() = when (this) {
    is InlineMessageIdDataCallbackQuery -> null
    is MessageDataCallbackQuery -> message.chat
    is InlineMessageIdGameShortNameCallbackQuery -> null
    is MessageGameShortNameCallbackQuery -> message.chat
    is UnknownCallbackQueryType -> null
}

@PreviewFeature
fun Update.sourceChatWithConverters(
    baseMessageUpdateConverter: (BaseMessageUpdate) -> Chat? = { it.data.chat },
    chatJoinRequestUpdateConverter: (ChatJoinRequestUpdate) -> Chat? = { it.data.chat },
    shippingQueryUpdateConverter: (ShippingQueryUpdate) -> Chat? = { null },
    pollAnswerUpdateConverter: (PollAnswerUpdate) -> Chat? = { null },
    preCheckoutQueryUpdateConverter: (PreCheckoutQueryUpdate) -> Chat? = { it.data.from },
    callbackQueryUpdateConverter: (CallbackQueryUpdate) -> Chat? = { it.data.sourceChat() },
    chosenInlineResultUpdateConverter: (ChosenInlineResultUpdate) -> Chat? = { null },
    inlineQueryUpdateConverter: (InlineQueryUpdate) -> Chat? = { null },
    pollUpdateConverter: (PollUpdate) -> Chat? = { null },
    channelPostUpdateConverter: (ChannelPostUpdate) -> Chat? = { it.data.chat },
    messageUpdateConverter: (MessageUpdate) -> Chat? = { it.data.chat },
    editChannelPostUpdateConverter: (EditChannelPostUpdate) -> Chat? = { it.data.chat },
    editMessageUpdateConverter: (EditMessageUpdate) -> Chat? = { it.data.chat },
    myChatMemberUpdatedUpdateConverter: (MyChatMemberUpdatedUpdate) -> Chat? = { it.data.chat },
    commonChatMemberUpdatedUpdateConverter: (CommonChatMemberUpdatedUpdate) -> Chat? = { it.data.chat }
): Chat? = when (this) {
    is BaseMessageUpdate -> baseMessageUpdateConverter(this)
    is ChatJoinRequestUpdate -> chatJoinRequestUpdateConverter(this)
    is ShippingQueryUpdate -> shippingQueryUpdateConverter(this)
    is PollAnswerUpdate -> pollAnswerUpdateConverter(this)
    is PreCheckoutQueryUpdate -> preCheckoutQueryUpdateConverter(this)
    is CallbackQueryUpdate -> callbackQueryUpdateConverter(this)
    is ChosenInlineResultUpdate -> chosenInlineResultUpdateConverter(this)
    is InlineQueryUpdate -> inlineQueryUpdateConverter(this)
    is PollUpdate -> pollUpdateConverter(this)
    is ChannelPostUpdate -> channelPostUpdateConverter(this)
    is MessageUpdate -> messageUpdateConverter(this)
    is EditChannelPostUpdate -> editChannelPostUpdateConverter(this)
    is EditMessageUpdate -> editMessageUpdateConverter(this)
    is MyChatMemberUpdatedUpdate -> myChatMemberUpdatedUpdateConverter(this)
    is CommonChatMemberUpdatedUpdate -> commonChatMemberUpdatedUpdateConverter(this)
    else -> {
        when (val data = data) {
            is FromUser -> data.from
            is WithUser -> data.user
            else -> null
        }
    }
}

@PreviewFeature
fun Update.sourceChat(): Chat? = sourceChatWithConverters()

@PreviewFeature
fun Update.sourceUser(): User? = when (val data = data) {
    is FromUser -> data.from
    is WithUser -> data.user
    else -> sourceChat()?.asUser()
}
