package es.javiergimenez.chat.model.event

import es.javiergimenez.chat.model.Chat
import es.javiergimenez.chat.model.Message
import java.io.Serializable


data class MessageEvent(

    val message: Message,
    val chat: Chat

): Serializable