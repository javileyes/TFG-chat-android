package es.javiergimenez.chat.service

import android.content.Intent
import android.widget.Toast
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.controller.LoginActivity
import es.javiergimenez.chat.controller.MainActivity
import es.javiergimenez.chat.controller.RegisterActivity
import es.javiergimenez.chat.controller.SplashActivity
import es.javiergimenez.chat.model.User
import es.javiergimenez.chat.service.retrofit.ChatRetrofit
import es.javiergimenez.chat.service.tool.ProgressBarTool
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object LoginService {


    fun postAutoLogin(splashActivity: SplashActivity, autoLogin: User) {

        val dialog = ProgressBarTool.create(splashActivity)
        dialog.show()

        splashActivity.compositeDisposable.add(
            ChatRetrofit.chatApi.postLogin(autoLogin.username!!, autoLogin.password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dialog.dismiss()
                    Application.session.token = it.token
                    Application.session.user = it.user
                    splashActivity.startActivity(Intent(splashActivity, MainActivity::class.java))
                    splashActivity.overridePendingTransition(0, 0)
                    splashActivity.finish()
                }, {
                    dialog.dismiss()
                    splashActivity.startActivity(Intent(splashActivity, LoginActivity::class.java))
                    splashActivity.overridePendingTransition(0, 0)
                    splashActivity.finish()
                })
        )
    }

    fun postLogin(loginActivity: LoginActivity, username: String, password: String) {

        val dialog = ProgressBarTool.create(loginActivity)
        dialog.show()

        loginActivity.compositeDisposable.add(

            ChatRetrofit.chatApi.postLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dialog.dismiss()
                    Application.session.autoLogin = User(username = username, password = password)
                    Application.session.token = it.token
                    Application.session.user = it.user
                    loginActivity.startActivity(Intent(loginActivity, MainActivity::class.java))
                    loginActivity.finish()
                    loginActivity.overridePendingTransition(0, 0)
                }, {
                    dialog.dismiss()
                    Toast.makeText(loginActivity, "Usuario o contraseña no válido", Toast.LENGTH_SHORT).show()
                })
        )
    }

    fun postSingUp(registerActivity: RegisterActivity, username: String, password: String) {

        if (username.isBlank() || username.length < 4 || password.isBlank() || password.length < 4) {
            Toast.makeText(registerActivity, "Introduce un usuario y contraseña válidos", Toast.LENGTH_SHORT).show()
            return
        }

        val dialog = ProgressBarTool.create(registerActivity)
        dialog.show()

        registerActivity.compositeDisposable.add(

            ChatRetrofit.chatApi.postSingUp(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dialog.dismiss()
                    it.password = null
                    Application.session.autoLogin = it
                    registerActivity.onBackPressed()
                }, {
                    dialog.dismiss()
                    Toast.makeText(registerActivity, "Usuario ya existe", Toast.LENGTH_SHORT).show()
                })
        )

    }

}