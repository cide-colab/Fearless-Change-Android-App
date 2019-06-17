package de.thkoeln.colab.fearlesschange.ui.plugins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import kotlinx.android.synthetic.main.pattern_of_the_day_fragment.*

class PatternOfTheDayFragment : BasicPatternFragment<PatternOfTheDayViewModel>() {

    companion object {
        fun newInstance() = PatternOfTheDayFragment()
    }

    private val cardPreviewAdapter = PatternCardPreviewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pattern_of_the_day_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cardPreviewAdapter.onCardClickedListener = viewModel.patternCardClicked
        card_preview.setAdapter(cardPreviewAdapter)

        viewModel.patternOfTheDay.observe(this) { cardPreviewAdapter.change(it) }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(PatternOfTheDayViewModel::class.java)
}
