package ru.gravitana.gvd_universe.pod.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.transition.Slide
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_pod.*
import kotlinx.android.synthetic.main.fragment_pod.bottom_app_bar
import kotlinx.android.synthetic.main.fragment_pod.chip_of_days
import kotlinx.android.synthetic.main.fragment_pod.fab
import kotlinx.android.synthetic.main.fragment_pod_coordinator.*
import kotlinx.android.synthetic.main.fragment_pod_coordinator.podDescription
import kotlinx.android.synthetic.main.fragment_pod_coordinator.podDescriptionTitle
import kotlinx.android.synthetic.main.fragment_pod_start.*
import ru.gravitana.gvd_universe.main.view.BottomNavigationDrawerFragment
import ru.gravitana.gvd_universe.main.view.MainActivity
import ru.gravitana.gvd_universe.utils.EquilateralImageView
import ru.gravitana.gvd_universe.R
import ru.gravitana.gvd_universe.api.ApiActivity
import ru.gravitana.gvd_universe.settings.view.SettingsFragment
import ru.gravitana.gvd_universe.databinding.FragmentPodBinding
import ru.gravitana.gvd_universe.pod.model.PictureOfTheDayData
import ru.gravitana.gvd_universe.pod.viewmodel.PictureOfTheDayViewModel
import ru.gravitana.gvd_universe.settings.view.ExamplesFragment
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPodBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private val now = System.currentTimeMillis()
    private val formatter = SimpleDateFormat("yyyy-MM-dd")
    private var daysAgo = 0

    private var textIsVisible = false

    private fun getTargetDate(daysAgo: Int) = formatter.format(now - 86400000 * daysAgo)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(getTargetDate(daysAgo)).observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)
        wiki_input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.wikiInputEditText.text.toString()}")
            })
        }
        chip_of_days.setOnCheckedChangeListener { chipGroup, position ->
            chip_of_days.findViewById<Chip>(position)?.let {
                daysAgo = chip_of_days.childCount - it.id
                onActivityCreated(savedInstanceState)
            }
        }
        fabSecond.setOnClickListener {
            TransitionManager.beginDelayedTransition(transitions_container, Slide(Gravity.START))
            textIsVisible = !textIsVisible
            wiki_input_layout.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            wiki_input_edit_text.setText(podDescriptionTitle.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_api -> startActivity(Intent(activity, ApiActivity::class.java))
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, SettingsFragment())?.addToBackStack(null)?.commit()
            R.id.app_bar_examples -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, ExamplesFragment())?.addToBackStack(null)?.commit()
            R.id.app_bar_search -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, WikiFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.bottom_menu)
            }
        }
    }

    private fun renderData(data: PictureOfTheDayData) {

        val imageView = requireView().findViewById(R.id.image_view) as EquilateralImageView

        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, R.string.err_empty_url, Toast.LENGTH_LONG).show()
                    imageView.load(R.drawable.ic_no_photo_vector)
                } else {
                    imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_error)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }

                    podDescriptionTitle.setText(serverResponseData.title)
                    podDescription.setText(serverResponseData.explanation)
                }
            }
            is PictureOfTheDayData.Loading -> {
                imageView.load(R.drawable.ic_baseline_hourglass_empty_24)
            }
            is PictureOfTheDayData.Error -> {
                imageView.load(R.drawable.ic_error2)
                Toast.makeText(context, R.string.err_photo_not_loaded, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

}