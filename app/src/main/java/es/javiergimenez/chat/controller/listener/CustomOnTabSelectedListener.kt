package es.javiergimenez.chat.controller.listener

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class CustomOnTabSelectedListener(
    var viewPager: ViewPager
): TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager.currentItem = tab.position
    }

    override fun onTabReselected(tab: TabLayout.Tab) { }

    override fun onTabUnselected(tab: TabLayout.Tab) { }

}