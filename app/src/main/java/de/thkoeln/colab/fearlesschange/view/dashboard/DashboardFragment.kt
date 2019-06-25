package de.thkoeln.colab.fearlesschange.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.view.dashboard.plugins.MostClickedCardFragment
import de.thkoeln.colab.fearlesschange.view.dashboard.plugins.PatternOfTheDayFragment
import de.thkoeln.colab.fearlesschange.view.dashboard.plugins.RandomPatternFragment
import kotlinx.android.synthetic.main.dashboard_fragment.*

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private val features = listOf(
            "CardOfTheDay" to PatternOfTheDayFragment.newInstance(),
            "RandomPattern" to RandomPatternFragment.newInstance(),
            "MostClickedCard" to MostClickedCardFragment.newInstance()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        inflateFeatureFragments()
    }

    private fun inflateFeatureFragments() {
        with(childFragmentManager.beginTransaction()) {
            features.forEach {
                if (childFragmentManager.findFragmentByTag(it.first) == null) {
                    add(dashboard_feature_list.id, it.second, it.first)
                }
            }
            commit()
        }
    }
}
