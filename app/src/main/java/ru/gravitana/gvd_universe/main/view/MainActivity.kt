package ru.gravitana.gvd_universe.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gravitana.gvd_universe.R
import ru.gravitana.gvd_universe.main.CURRENT_THEME_KEY
import ru.gravitana.gvd_universe.main.DEFAULT_THEME_ID
import ru.gravitana.gvd_universe.pod.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(this.getPreferences(Context.MODE_PRIVATE).getInt(CURRENT_THEME_KEY, DEFAULT_THEME_ID))

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}