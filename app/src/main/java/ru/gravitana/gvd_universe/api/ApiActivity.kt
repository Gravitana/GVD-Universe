package ru.gravitana.gvd_universe.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_api.*
import ru.gravitana.gvd_universe.R

class ApiActivity : AppCompatActivity() {

    private val layouts = mapOf(
        EARTH to R.layout.activity_api_custom_tab_earth,
        MARS to R.layout.activity_api_custom_tab_mars,
        WEATHER to R.layout.activity_api_custom_tab_weather
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        setHighlightedTab(EARTH)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float,  positionOffsetPixels: Int) {}
        })


    }

    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@ApiActivity)

        var i = 0
        while (i < tab_layout.tabCount){
            tab_layout.getTabAt(i)?.customView = null
            i++
        }

        setTabHighlighted(position, layoutInflater)
    }

    private fun setTabHighlighted(position: Int, layoutInflater: LayoutInflater?) {

        val activeTab = layoutInflater!!.inflate(layouts.get(position) ?: 0, null)
        activeTab.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )

        var i = 0
        while (i < tab_layout.tabCount){
            if (i == position) {
                tab_layout.getTabAt(i)?.customView = activeTab
            } else {
                tab_layout.getTabAt(i)?.customView =
                    layouts.get(i)?.let { layoutInflater.inflate(it, null) }
            }
            i++
        }
    }
}