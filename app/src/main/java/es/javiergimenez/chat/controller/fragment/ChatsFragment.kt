package es.javiergimenez.chat.controller.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiergimenez.chat.R
import es.javiergimenez.chat.controller.adapter.ChatsAdapter
import es.javiergimenez.chat.model.event.MessageEvent
import es.javiergimenez.chat.service.ChatService
import kotlinx.android.synthetic.main.fragment_users.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ChatsFragment: Fragment() {

    lateinit var chatsAdapter: ChatsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatsAdapter = ChatsAdapter(activity!!)
        listView.adapter = chatsAdapter
    }

    override fun onStart() {
        super.onStart()
        ChatService.getChats(this)
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        val chat = chatsAdapter.list.stream()
            .filter { messageEvent.chat.id == it.id }
            .findAny()
            .orElse(null)

        val list = chatsAdapter.list

        if (chat == null) {
            messageEvent.chat.lastMessage = messageEvent.message
            list.add(messageEvent.chat)
        } else {
            chat.lastMessage = messageEvent.message
        }

        chatsAdapter.clearAndUpdate(list.sortedBy { it.lastMessage!!.date }.reversed())
    }

}