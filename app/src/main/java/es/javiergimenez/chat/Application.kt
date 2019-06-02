package es.javiergimenez.chat

import com.google.firebase.FirebaseApp


class Application : android.app.Application() {

    companion object {
        lateinit var session: Session
    }

    override fun onCreate() {
        super.onCreate()
        session = Session.create(this)
        FirebaseApp.initializeApp(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Session.store(this, session)
    }

}