package es.javiergimenez.chat.service.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.model.Chat
import es.javiergimenez.chat.model.Message
import es.javiergimenez.chat.model.User
import es.javiergimenez.chat.model.event.MessageEvent
import es.javiergimenez.chat.service.deserializer.DateTimeDeserializer
import es.javiergimenez.chat.service.tool.PushTool
import org.greenrobot.eventbus.EventBus
import org.joda.time.DateTime


class FirebaseMessageService: FirebaseMessagingService() {

    enum class Type {
        MESSAGE
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        println("FIREBASE SERVICE: onMessageReceived")

        val data = remoteMessage!!.data

        if (data.containsKey("type")) {
            val type = Type.valueOf(data["type"]!!)
            when (type) {
                Type.MESSAGE -> {

                    val message = Message()
                    message.id = data["id"]!!.toLong()
                    message.body = data["body"]
                    message.date = DateTime.parse(data["date"], DateTimeDeserializer.formatter)
                    message.user = User(data["userId"]!!.toLong(), data["username"])

                    val chat = Chat(data["chatId"]!!.toLong(), data["channelId"], data["title"])

                    EventBus.getDefault().post(MessageEvent(message, chat))

                    if (Application.session.user!!.id != message.user!!.id) {
                        PushTool.show(applicationContext, remoteMessage.notification)
                    }
                }
            }
        }

    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        println("FIREBASE SERVICE: sendRegistrationToServer " + token!!)
    }
}