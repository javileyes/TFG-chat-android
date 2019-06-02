package es.javiergimenez.chat.controller.adapter

import android.app.Activity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.R
import es.javiergimenez.chat.model.Message
import kotlinx.android.synthetic.main.item_message.view.*


class MessagesAdapter(
    activity: Activity
): CustomBaseAdapter<Message, MessagesAdapter.Holder>(activity, R.layout.item_message, Holder::class.java) {


    class Holder(view: View) {
        val meRelativeLayout: RelativeLayout = view.meRelativeLayout
        val meBodyTextView: TextView = view.meBodyTextView
        val meDateTextView: TextView = view.meDateTextView
        val linearLayout: LinearLayout = view.linearLayout
        val bodyTextView: TextView = view.bodyTextView
        val dateTextView: TextView = view.dateTextView
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val message = list[position]

        if (message.user!!.id == Application.session.user!!.id) {
            holder.meRelativeLayout.visibility = View.VISIBLE
            holder.linearLayout.visibility = View.GONE
            holder.meBodyTextView.text = message.body
            holder.meDateTextView.text = message.date!!.toString("HH:mm")
        } else {
            holder.meRelativeLayout.visibility = View.GONE
            holder.linearLayout.visibility = View.VISIBLE
            holder.bodyTextView.text = message.body
            holder.dateTextView.text = message.date!!.toString("HH:mm")
        }


    }

}