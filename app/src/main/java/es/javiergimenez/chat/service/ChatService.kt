package es.javiergimenez.chat.service

import android.view.View
import android.widget.Toast
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.controller.MainActivity
import es.javiergimenez.chat.controller.fragment.ChatsFragment
import es.javiergimenez.chat.service.retrofit.ChatRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_users.*


object ChatService {


    fun getChats(chatsFragment: ChatsFragment) {

        chatsFragment.progressBar.visibility = View.VISIBLE

        (chatsFragment.activity as MainActivity).compositeDisposable.add(

            ChatRetrofit.chatApi.getChats(ChatRetrofit.BEARER + Application.session.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    chatsFragment.progressBar.visibility = View.GONE
                    chatsFragment.chatsAdapter.clearAndUpdate(it.sortedBy { chat -> chat.lastMessage!!.date }.reversed())
                }, {
                    chatsFragment.progressBar.visibility = View.GONE
                    Toast.makeText(chatsFragment.activity, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show()
                })
        )
    }

    fun getChannelId(userId: Long): String {
        return if (userId > Application.session.user!!.id!!) {
            "user_${Application.session.user!!.id!!}_user_$userId"
        } else {
            "user_${userId}_user_${Application.session.user!!.id!!}"
        }
    }

}