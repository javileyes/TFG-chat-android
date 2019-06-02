package es.javiergimenez.chat.service

import android.widget.Toast
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.controller.ChatActivity
import es.javiergimenez.chat.service.retrofit.ChatRetrofit
import es.javiergimenez.chat.service.tool.ProgressBarTool
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.*


object MessageService {


    fun getMessages(chatActivity: ChatActivity, channelId: String) {

        val dialog = ProgressBarTool.create(chatActivity)
        dialog.show()

        chatActivity.compositeDisposable.add(
            ChatRetrofit.chatApi.getMessages(ChatRetrofit.BEARER + Application.session.token, channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dialog.dismiss()
                    if (it.isSuccessful) {
                        chatActivity.messagesAdapter.clearAndUpdate(it.body()!!)
                        chatActivity.listView.setSelection(chatActivity.messagesAdapter.count - 1)
                    }
                }, {
                    Toast.makeText(chatActivity, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show()
                })
        )

    }

    fun postMessage(chatActivity: ChatActivity, channelId: String, body: String) {
        val dialog = ProgressBarTool.create(chatActivity)
        dialog.show()

        chatActivity.compositeDisposable.add(
            ChatRetrofit.chatApi.postMessage(ChatRetrofit.BEARER + Application.session.token, channelId, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dialog.dismiss()
                    if (!chatActivity.messagesAdapter.list.stream().filter { message -> message.id == it.id }.findAny().isPresent) {
                        chatActivity.messagesAdapter.update(listOf(it))
                    }
                    chatActivity.listView.setSelection(chatActivity.messagesAdapter.count - 1)
                    chatActivity.sendEditText.text.clear()
                }, {
                    Toast.makeText(chatActivity, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show()
                })
        )

    }

}