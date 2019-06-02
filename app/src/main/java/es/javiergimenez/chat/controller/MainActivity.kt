package es.javiergimenez.chat.controller

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import es.javiergimenez.chat.Application
import es.javiergimenez.chat.controller.adapter.CustomPagerAdapter
import es.javiergimenez.chat.controller.fragment.ChatsFragment
import es.javiergimenez.chat.controller.fragment.UsersFragment
import es.javiergimenez.chat.controller.listener.CustomOnTabSelectedListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_header.*
import kotlinx.android.synthetic.main.include_sidemenu.*




class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(es.javiergimenez.chat.R.layout.activity_main)
        configureViews()

        FirebaseMessaging.getInstance().subscribeToTopic("chat")
        FirebaseMessaging.getInstance().subscribeToTopic("user_${Application.session.user!!.id!!}")
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun configureViews() {
        typefaceCondensed(tabLayout)

        val fragments = ArrayList<Fragment>()
        fragments.add(UsersFragment())
        fragments.add(ChatsFragment())
        viewPager.adapter = CustomPagerAdapter(supportFragmentManager, fragments)
        viewPager.offscreenPageLimit = 1
        tabLayout.addOnTabSelectedListener(
            CustomOnTabSelectedListener(
                viewPager
            )
        )
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        sideMenuIconView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        logoutIconView.setOnClickListener {
            Application.session.autoLogin = null
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        usernameTextView.text = "@${Application.session.user!!.username}"
    }

    private fun typefaceCondensed(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildCount = vgTab.childCount
            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
                }
            }
        }
    }
}
