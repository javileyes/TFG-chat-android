package es.javiergimenez.chat.model

import org.joda.time.DateTime
import java.io.Serializable


data class Message(

    var id: Long? = null,
    var body: String? = null,
    var user: User? = null,
    var date: DateTime? = null

): Serializable