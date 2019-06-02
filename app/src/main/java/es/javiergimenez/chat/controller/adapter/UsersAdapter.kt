package es.javiergimenez.chat.controller.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import es.javiergimenez.chat.R
import es.javiergimenez.chat.controller.ChatActivity
import es.javiergimenez.chat.model.Chat
import es.javiergimenez.chat.model.User
import es.javiergimenez.chat.service.ChatService
import es.javiergimenez.chat.service.tool.ColorTool
import es.javiergimenez.chat.service.tool.IconValueTool
import kotlinx.android.synthetic.main.item_row.view.*
import net.steamcrafted.materialiconlib.MaterialIconView


class UsersAdapter(
    activity: Activity
    ): CustomBaseAdapter<User, UsersAdapter.Holder>(activity, R.layout.item_row, Holder::class.java) {


    class Holder(view: View) {
        val linearLayout: LinearLayout = view.linearLayout
        val iconView: MaterialIconView = view.iconView
        val titleTextView: TextView = view.titleTextView
        val bodyTextView: TextView = view.bodyTextView
        val bodyEndTextView: TextView = view.bodyEndTextView
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = list[position]

        holder.iconView.setBackgroundResource(ColorTool.getColorResourceByText(user.username!!))
        holder.iconView.setIcon(IconValueTool.getIconValueByText(user.username!!))

        holder.titleTextView.text = user.username
        holder.bodyTextView.visibility = View.INVISIBLE
        holder.bodyEndTextView.visibility = View.INVISIBLE
        holder.linearLayout.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            val chat = Chat()
            chat.title = user.username
            chat.channelId = ChatService.getChannelId(user.id!!)

            intent.putExtra(Chat::class.java.simpleName, chat)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

}