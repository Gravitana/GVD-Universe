package ru.gravitana.gvd_universe.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gravitana.gvd_universe.R
import ru.gravitana.gvd_universe.pod.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setTheme(R.style.GVDUniverse_GreenTheme)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}