package es.javiergimenez.chat.controller.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class CustomPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: List<Fragment>
): FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}