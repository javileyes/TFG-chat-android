package es.javiergimenez.chat.controller.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.R
import es.javiergimenez.chat.controller.ChatActivity
import es.javiergimenez.chat.model.Chat
import es.javiergimenez.chat.service.deserializer.DateTimeDeserializer
import es.javiergimenez.chat.service.tool.ColorTool
import es.javiergimenez.chat.service.tool.IconValueTool
import kotlinx.android.synthetic.main.item_row.view.*
import net.steamcrafted.materialiconlib.MaterialIconView


class ChatsAdapter(
    activity: Activity
): CustomBaseAdapter<Chat, ChatsAdapter.Holder>(activity, R.layout.item_row, Holder::class.java) {


    class Holder(view: View) {
        val linearLayout: LinearLayout = view.linearLayout
        val iconView: MaterialIconView = view.iconView
        val titleTextView: TextView = view.titleTextView
        val bodyTextView: TextView = view.bodyTextView
        val bodyEndTextView: TextView = view.bodyEndTextView
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val chat = list[position]

        if (chat.title == null) {
            chat.title = "push"
        }

        holder.iconView.setBackgroundResource(ColorTool.getColorResourceByText(chat.title!!))
        holder.iconView.setIcon(IconValueTool.getIconValueByText(chat.title!!))
        holder.titleTextView.text = chat.title
        val username = if (chat.lastMessage!!.user!!.id == Application.session.user!!.id) "yo" else chat.lastMessage!!.user!!.username
        holder.bodyTextView.text = "$username: ${chat.lastMessage!!.body}"
        holder.bodyEndTextView.text = DateTimeDeserializer.timeAgo(chat.lastMessage!!.date!!)
        holder.linearLayout.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(Chat::class.java.simpleName, chat)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

}