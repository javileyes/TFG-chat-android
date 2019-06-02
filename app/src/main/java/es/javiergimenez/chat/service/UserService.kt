package es.javiergimenez.chat.service

import android.view.View
import android.widget.Toast
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.controller.MainActivity
import es.javiergimenez.chat.controller.fragment.UsersFragment
import es.javiergimenez.chat.service.retrofit.ChatRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_users.*
import java.util.stream.Collectors


object UserService {


    fun getUsers(usersFragment: UsersFragment) {

        usersFragment.progressBar.visibility = View.VISIBLE

        (usersFragment.activity as MainActivity).compositeDisposable.add(

            ChatRetrofit.chatApi.getUsers(ChatRetrofit.BEARER + Application.session.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    usersFragment.progressBar.visibility = View.GONE

                    usersFragment.usersAdapter.clearAndUpdate(
                        it.stream()
                            .filter { user ->
                                user.id != Application.session.user!!.id
                            }
                            .collect(Collectors.toList())
                    )
                }, {
                    usersFragment.progressBar.visibility = View.GONE
                    Toast.makeText(usersFragment.activity, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show()
                })
        )

    }


}