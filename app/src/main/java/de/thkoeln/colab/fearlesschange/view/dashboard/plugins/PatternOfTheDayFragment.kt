package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.observe
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import kotlinx.android.synthetic.main.pattern_of_the_day_fragment.*

class PatternOfTheDayFragment : InteractiveFragment<PatternOfTheDayViewModel>() {

    companion object {
        fun newInstance() = PatternOfTheDayFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pattern_of_the_day_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pattern_of_the_day_pattern_card.setOnClickListener {
            viewModel.patternCardClicked(pattern_of_the_day_pattern_card.patternPreviewData)
        }
        viewModel.patternOfTheDayData.observe(this) {
            pattern_of_the_day_pattern_card.patternPreviewData = it

        }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(PatternOfTheDayViewModel::class.java)
}
