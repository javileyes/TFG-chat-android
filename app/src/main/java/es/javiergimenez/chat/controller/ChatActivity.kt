package es.javiergimenez.chat.controller

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import es.javiergimenez.chat.controller.adapter.MessagesAdapter
import es.javiergimenez.chat.model.Chat
import es.javiergimenez.chat.model.event.MessageEvent
import es.javiergimenez.chat.service.MessageService
import es.javiergimenez.chat.service.tool.ColorTool
import es.javiergimenez.chat.service.tool.IconValueTool
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.include_header.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ChatActivity: AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()
    lateinit var messagesAdapter: MessagesAdapter
    lateinit var chat: Chat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(es.javiergimenez.chat.R.layout.activity_chat)

        chat = intent.getSerializableExtra(Chat::class.java.simpleName) as Chat

        messagesAdapter = MessagesAdapter(this)
        listView.adapter = messagesAdapter

        sideMenuIconView.visibility = View.GONE
        backIconView.visibility = View.VISIBLE
        backIconView.setOnClickListener {
            onBackPressed()
        }

        sendIconView.setOnClickListener {
            if (!sendEditText.text.isBlank()) {
                MessageService.postMessage(this, chat.channelId!!, sendEditText.text.toString())
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(sendEditText, InputMethodManager.SHOW_FORCED)
            }
        }

        sendEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendIconView.performClick()
            }
            true
        }

        titleTextView.text = chat.title
        cardView.visibility = View.VISIBLE
        iconView.setBackgroundResource(ColorTool.getColorResourceByText(chat.title))
        iconView.setIcon(IconValueTool.getIconValueByText(chat.title))

        MessageService.getMessages(this, chat.channelId!!)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        if (chat.channelId == messageEvent.chat.channelId
            && !messagesAdapter.list.stream().filter { messageEvent.message.id == it.id }.findAny().isPresent) {
            messagesAdapter.update(listOf(messageEvent.message))
        }
    }

}