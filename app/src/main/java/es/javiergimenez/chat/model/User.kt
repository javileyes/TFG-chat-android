package es.javiergimenez.chat.model

import java.io.Serializable


data class User(

    var id: Long? = null,
    var username: String? = null,
    var password: String? = null

): Serializable