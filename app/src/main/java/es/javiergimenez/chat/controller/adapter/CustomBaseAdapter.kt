package es.javiergimenez.chat.controller.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter


abstract class CustomBaseAdapter<T, VH>(

    var activity: Activity,
    var itemResource: Int,
    var holderClass: Class<VH>

) : BaseAdapter() {

    var list: MutableList<T> = mutableListOf()


    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun update(list: List<T>? = null) {
        if (list != null) {
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun clearAndUpdate(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any? {
        return if (list.isEmpty()) {
            null
        } else {
            list[i]
        }
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val view: View
        val holder: VH
        if (convertView == null) {
            view = LayoutInflater.from(activity).inflate(itemResource, viewGroup, false)
            holder = holderClass.getConstructor(View::class.java).newInstance(view)
            view.tag = holder
        } else {
            view = convertView
            @Suppress("UNCHECKED_CAST")
            holder = convertView.tag as VH
        }
        onBindViewHolder(holder, position)
        return view
    }

    abstract fun onBindViewHolder(holder: VH, position: Int)

}