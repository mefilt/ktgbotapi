package dev.inmo.tgbotapi.types.message.content.media

import dev.inmo.tgbotapi.CommonAbstracts.TextPart
import dev.inmo.tgbotapi.requests.abstracts.Request
import dev.inmo.tgbotapi.requests.send.media.SendAudio
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.InputMedia.*
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.HTMLParseMode
import dev.inmo.tgbotapi.types.ParseMode.MarkdownV2
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.files.AudioFile
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.abstracts.*
import dev.inmo.tgbotapi.utils.toHtmlCaptions
import dev.inmo.tgbotapi.utils.toMarkdownV2Captions

data class AudioContent(
    override val media: AudioFile,
    override val caption: String? = null,
    override val captionEntities: List<TextPart> = emptyList()
) : MediaGroupContent {
    override fun createResend(
        chatId: ChatIdentifier,
        disableNotification: Boolean,
        replyToMessageId: MessageIdentifier?,
        replyMarkup: KeyboardMarkup?
    ): Request<ContentMessage<AudioContent>> = SendAudio(
        chatId,
        media.fileId,
        media.thumb ?.fileId,
        toHtmlCaptions().firstOrNull(),
        HTMLParseMode,
        media.duration,
        media.performer,
        media.title,
        disableNotification,
        replyToMessageId,
        replyMarkup
    )

    override fun toMediaGroupMemberInputMedia(): MediaGroupMemberInputMedia = media.toInputMediaAudio(
        toHtmlCaptions().firstOrNull(),
        HTMLParseMode
    )

    override fun asInputMedia(): InputMediaAudio = InputMediaAudio(
        media.fileId,
        toMarkdownV2Captions().firstOrNull(),
        MarkdownV2,
        media.duration,
        media.performer,
        media.title,
        media.thumb ?.fileId
    )
}
