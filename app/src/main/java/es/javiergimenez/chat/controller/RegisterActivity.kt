package es.javiergimenez.chat.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiergimenez.chat.R
import es.javiergimenez.chat.service.LoginService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity: AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        enterButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            LoginService.postSingUp(this, username, password)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}