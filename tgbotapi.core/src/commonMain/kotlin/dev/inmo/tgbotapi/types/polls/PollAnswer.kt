package dev.inmo.tgbotapi.types.polls

import dev.inmo.tgbotapi.abstracts.FromUser
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.chat.ChannelChat
import dev.inmo.tgbotapi.types.chat.CommonBot
import dev.inmo.tgbotapi.types.chat.User
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(PollAnswer.Companion::class)
sealed interface PollAnswer: FromUser {
    val pollId: PollIdentifier
    override val user: User
    val chosen: List<Int>
    @Transient
    override val from: User
        get() = user

    @Serializable
    data class Public(
        @SerialName(pollIdField)
        override val pollId: PollIdentifier,
        @SerialName(userField)
        override val user: User,
        @SerialName(optionIdsField)
        override val chosen: List<Int>,
    ) : PollAnswer

    @Serializable
    data class Anonymous(
        @SerialName(pollIdField)
        override val pollId: PollIdentifier,
        @SerialName(voterChatField)
        val voterChat: ChannelChat,
        @SerialName(optionIdsField)
        override val chosen: List<Int>
    ) : PollAnswer {
        @SerialName(userField)
        override val user: User = defaultUser

        companion object {
            val defaultUser = CommonBot(
                UserId(136817688L),
                "",
                "",
                Username("@Channel_Bot")
            )
        }
    }

    companion object : KSerializer<PollAnswer> {
        @Serializable
        private data class PollAnswerSurrogate(
            @SerialName(pollIdField)
            val pollId: PollIdentifier,
            @SerialName(optionIdsField)
            val chosen: List<Int>,
            @SerialName(userField)
            val user: User = Anonymous.defaultUser,
            @SerialName(voterChatField)
            val voterChat: ChannelChat? = null
        )
        operator fun invoke(
            pollId: PollIdentifier,
            user: User,
            chosen: List<Int>,
        ) = Public(pollId, user, chosen)

        override val descriptor: SerialDescriptor
            get() = PollAnswerSurrogate.serializer().descriptor

        override fun deserialize(decoder: Decoder): PollAnswer {
            val surrogate = PollAnswerSurrogate.serializer().deserialize(decoder)
            return if (surrogate.voterChat != null) {
                Anonymous(surrogate.pollId, surrogate.voterChat, surrogate.chosen)
            } else {
                Public(surrogate.pollId, surrogate.user, surrogate.chosen)
            }
        }

        override fun serialize(encoder: Encoder, value: PollAnswer) {
            PollAnswerSurrogate.serializer().serialize(
                encoder,
                PollAnswerSurrogate(
                    value.pollId,
                    value.chosen,
                    value.user,
                    (value as? Anonymous) ?.voterChat
                )
            )
        }
    }
}
