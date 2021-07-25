package ru.gravitana.gvd_universe.settings.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.gravitana.gvd_universe.R
import ru.gravitana.gvd_universe.main.CURRENT_THEME_KEY
import ru.gravitana.gvd_universe.main.DEFAULT_THEME_ID
import ru.gravitana.gvd_universe.main.THEMES

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseThemeGroup.setOnCheckedChangeListener { chooseThemeGroup, position ->
            chooseThemeGroup.findViewById<Chip>(position)?.let {
                val themeId = THEMES.get(it.id) ?: DEFAULT_THEME_ID
                requireActivity()
                    .getPreferences(Context.MODE_PRIVATE)
                    .edit()
                    .putInt(CURRENT_THEME_KEY, themeId)
                    .apply()
            }
        }
    }

}