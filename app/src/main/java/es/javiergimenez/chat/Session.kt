package es.javiergimenez.chat

import android.app.Application
import es.javiergimenez.chat.model.User
import es.javiergimenez.chat.service.tool.StorageTool
import java.io.Serializable


class Session : Serializable {

    var token: String? = null
    var user: User? = null
    var autoLogin: User? = null


    companion object {

        private const val KEY_SESSION = "SESSION"

        fun create(application: Application): Session {
            val session: Session? = StorageTool.load(application, KEY_SESSION)
            return session ?: Session()
        }

        fun store(application: Application, session: Session) {
            StorageTool.store(application, session, KEY_SESSION)
        }

    }

}