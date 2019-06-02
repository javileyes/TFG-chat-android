package es.javiergimenez.chat.model

import java.io.Serializable


data class Chat(

    var id: Long? = null,
    var channelId: String? = null,
    var title: String? = null,
    var lastMessage: Message? = null


): Serializable