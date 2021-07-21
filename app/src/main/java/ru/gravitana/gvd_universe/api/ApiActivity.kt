package ru.gravitana.gvd_universe.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_api.*
import ru.gravitana.gvd_universe.R

class ApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}