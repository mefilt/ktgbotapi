package dev.inmo.tgbotapi.requests.send.abstracts

import dev.inmo.tgbotapi.abstracts.types.*

interface SendMessageRequest<T: Any> : SendChatMessageRequest<T>,
    ReplyMessageId,
    DisableNotification,
    ProtectContent,
    OptionallyMessageThreadRequest
