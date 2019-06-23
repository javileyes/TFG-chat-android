package es.javiergimenez.chat.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.R
import es.javiergimenez.chat.service.LoginService
import es.javiergimenez.chat.service.retrofit.ChatRetrofit
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        enterButton.setOnClickListener {
            var server = servernameEditText.text.toString()
            if (!server.contains("http://")) {
                server = "http://$server"
            }
            ChatRetrofit.BASE_URL = server
            if (ChatRetrofit.iniciar()) {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                LoginService.postLogin(this, username, password)
            } else Toast.makeText(this, "servidor no operativo: ${ChatRetrofit.BASE_URL}", Toast.LENGTH_SHORT).show()
        }

        registerButton.setOnClickListener {
            var server = servernameEditText.text.toString()
            if (!server.contains("http://")) {
                server = "http://$server"
            }
            ChatRetrofit.BASE_URL = server
            ChatRetrofit.iniciar()
            if (ChatRetrofit.iniciar()) {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                LoginService.postSingUp(this, username, password)
            } else Toast.makeText(this, "servidor no operativo: ${ChatRetrofit.BASE_URL}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        if (Application.session.autoLogin != null) {
            usernameEditText.setText(Application.session.autoLogin!!.username)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}