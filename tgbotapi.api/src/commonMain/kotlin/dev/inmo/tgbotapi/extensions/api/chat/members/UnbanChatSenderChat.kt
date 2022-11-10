package dev.inmo.tgbotapi.extensions.api.chat.members

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.requests.chat.members.UnbanChatSenderChat
import dev.inmo.tgbotapi.types.IdChatIdentifier
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.chat.PublicChat

suspend fun TelegramBot.unbanChatSenderChat(
    chatId: ChatIdentifier,
    senderChatId: IdChatIdentifier
) = execute(UnbanChatSenderChat(chatId, senderChatId))

suspend fun TelegramBot.unbanChatSenderChat(
    chat: PublicChat,
    senderChatId: IdChatIdentifier
) = unbanChatSenderChat(chat.id, senderChatId)

suspend fun TelegramBot.unbanChatSenderChat(
    chatId: IdChatIdentifier,
    senderChat: PublicChat
) = unbanChatSenderChat(chatId, senderChat.id)

suspend fun TelegramBot.unbanChatSenderChat(
    chat: PublicChat,
    senderChat: PublicChat,
) = unbanChatSenderChat(chat.id, senderChat)
