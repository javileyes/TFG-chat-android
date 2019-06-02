package es.javiergimenez.chat.model.response

import es.javiergimenez.chat.model.User
import java.io.Serializable


data class LoginResponse(

    var token: String,
    var user: User

): Serializable