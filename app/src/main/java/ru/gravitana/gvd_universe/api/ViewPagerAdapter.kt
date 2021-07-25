package ru.gravitana.gvd_universe.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH]
            1 -> fragments[MARS]
            2 -> fragments[WEATHER]
            else -> fragments[EARTH]
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }
}