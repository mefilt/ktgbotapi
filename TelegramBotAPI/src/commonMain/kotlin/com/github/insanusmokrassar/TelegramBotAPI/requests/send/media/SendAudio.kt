package com.github.insanusmokrassar.TelegramBotAPI.requests.send.media

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.Performerable
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.requests.send.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.requests.send.media.base.*
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.ParseMode.ParseMode
import com.github.insanusmokrassar.TelegramBotAPI.types.ParseMode.parseModeField
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.KeyboardMarkup
import com.github.insanusmokrassar.TelegramBotAPI.types.files.AudioFile
import com.github.insanusmokrassar.TelegramBotAPI.types.files.PhotoSize
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.ContentMessage
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.TelegramBotAPIMessageDeserializationStrategyClass
import com.github.insanusmokrassar.TelegramBotAPI.types.message.content.media.AudioContent
import com.github.insanusmokrassar.TelegramBotAPI.utils.mapOfNotNull
import kotlinx.serialization.*

fun SendAudio(
    chatId: ChatIdentifier,
    audio: InputFile,
    thumb: InputFile? = null,
    caption: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    performer: String? = null,
    title: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
): Request<ContentMessage<AudioContent>> {
    val audioAsFileId = (audio as? FileId) ?.fileId
    val audioAsFile = audio as? MultipartFile
    val thumbAsFileId = (thumb as? FileId) ?.fileId
    val thumbAsFile = thumb as? MultipartFile

    val data = SendAudioData(
        chatId,
        audioAsFileId,
        thumbAsFileId,
        caption,
        parseMode,
        duration,
        performer,
        title,
        disableNotification,
        replyToMessageId,
        replyMarkup
    )

    return if (audioAsFile == null && thumbAsFile == null) {
        data
    } else {
        MultipartRequestImpl(
            data,
            SendAudioFiles(audioAsFile, thumbAsFile)
        )
    }
}

private val commonResultDeserializer: DeserializationStrategy<ContentMessage<AudioContent>>
    = TelegramBotAPIMessageDeserializationStrategyClass()

@Serializable
data class SendAudioData internal constructor(
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier,
    @SerialName(audioField)
    val audio: String? = null,
    @SerialName(thumbField)
    override val thumb: String? = null,
    @SerialName(captionField)
    override val text: String? = null,
    @SerialName(parseModeField)
    override val parseMode: ParseMode? = null,
    @SerialName(durationField)
    override val duration: Long? = null,
    @SerialName(performerField)
    override val performer: String? = null,
    @SerialName(titleField)
    override val title: String? = null,
    @SerialName(disableNotificationField)
    override val disableNotification: Boolean = false,
    @SerialName(replyToMessageIdField)
    override val replyToMessageId: MessageIdentifier? = null,
    @SerialName(replyMarkupField)
    override val replyMarkup: KeyboardMarkup? = null
) : DataRequest<ContentMessage<AudioContent>>,
    SendMessageRequest<ContentMessage<AudioContent>>,
    ReplyingMarkupSendMessageRequest<ContentMessage<AudioContent>>,
    TextableSendMessageRequest<ContentMessage<AudioContent>>,
    ThumbedSendMessageRequest<ContentMessage<AudioContent>>,
    TitledSendMessageRequest<ContentMessage<AudioContent>>,
    DuratedSendMessageRequest<ContentMessage<AudioContent>>,
    Performerable
{
    init {
        text ?.let {
            if (it.length !in captionLength) {
                throw IllegalArgumentException("Caption must be in $captionLength range")
            }
        }
    }

    override fun method(): String = "sendAudio"
    override val resultDeserializer: DeserializationStrategy<ContentMessage<AudioContent>>
        get() = commonResultDeserializer
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}

data class SendAudioFiles internal constructor(
    val audio: MultipartFile? = null,
    val thumb: MultipartFile? = null
) : Files by mapOfNotNull(
    audioField to audio,
    thumbField to thumb
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: FileId,
    thumb: FileId? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    performer: String? = null,
    title: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    SendAudioData(
        chatId,
        audio.fileId,
        thumb ?.fileId,
        text,
        parseMode,
        duration,
        performer,
        title,
        disableNotification,
        replyToMessageId,
        replyMarkup
    )
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: AudioFile,
    thumb: PhotoSize? = audio.thumb,
    text: String? = null,
    parseMode: ParseMode? = null,
    title: String? = audio.title,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendAudio(
    chatId, audio.fileId, thumb ?.fileId, text, parseMode, audio.duration, audio.performer, title, disableNotification, replyToMessageId, replyMarkup
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: MultipartFile,
    thumb: FileId? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    performer: String? = null,
    title: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    MultipartRequestImpl(
        SendAudioData(
            chatId, null, thumb ?.fileId, text, parseMode, duration, performer, title, disableNotification, replyToMessageId, replyMarkup
        ),
        SendAudioFiles(audio)
    )
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: MultipartFile,
    thumb: MultipartFile? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    performer: String? = null,
    title: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    MultipartRequestImpl(
        SendAudioData(
            chatId, null, null, text, parseMode, duration, performer, title, disableNotification, replyToMessageId, replyMarkup
        ),
        SendAudioFiles(audio, thumb)
    )
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: FileId,
    thumb: MultipartFile,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    performer: String? = null,
    title: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    MultipartRequestImpl(
        SendAudioData(
            chatId, audio.fileId, null, text, parseMode, duration, performer, title, disableNotification, replyToMessageId, replyMarkup
        ),
        SendAudioFiles(null, thumb)
    )
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: MultipartFile,
    thumb: PhotoSize? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    performer: String? = null,
    title: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendAudio(
    chatId, audio, thumb ?.fileId , text, parseMode, duration, performer, title, disableNotification, replyToMessageId, replyMarkup
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.sendAudio(
    chatId: ChatIdentifier,
    audio: AudioFile,
    thumb: MultipartFile,
    text: String? = null,
    parseMode: ParseMode? = null,
    title: String? = audio.title,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendAudio(
    chatId, audio.fileId, thumb, text, parseMode, audio.duration, audio.performer, title, disableNotification, replyToMessageId, replyMarkup
)
