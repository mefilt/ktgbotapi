package dev.inmo.tgbotapi.types.games

import dev.inmo.tgbotapi.abstracts.TextedInput
import dev.inmo.tgbotapi.abstracts.Titled
import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.files.AnimationFile
import dev.inmo.tgbotapi.types.files.Photo
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    override val title: String,
    val description: String,
    val photo: Photo,
    override val text: String? = null,
    override val textSources: TextSourcesList = emptyList(),
    val animation: AnimationFile? = null
) : Titled, TextedInput
