package es.javiergimenez.chat.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.R
import es.javiergimenez.chat.service.LoginService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SplashActivity: AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Application.session.autoLogin != null && Application.session.autoLogin!!.password != null) {
            LoginService.postAutoLogin(this, Application.session.autoLogin!!)
        }

        compositeDisposable.add(
            Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .subscribe {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}