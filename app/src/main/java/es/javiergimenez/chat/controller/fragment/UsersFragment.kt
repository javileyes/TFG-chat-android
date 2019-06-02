package es.javiergimenez.chat.controller.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiergimenez.chat.R
import es.javiergimenez.chat.controller.adapter.UsersAdapter
import es.javiergimenez.chat.service.UserService
import kotlinx.android.synthetic.main.fragment_users.*


class UsersFragment: Fragment() {

    lateinit var usersAdapter: UsersAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersAdapter = UsersAdapter(activity!!)
        listView.adapter = usersAdapter

        UserService.getUsers(this)
    }


}