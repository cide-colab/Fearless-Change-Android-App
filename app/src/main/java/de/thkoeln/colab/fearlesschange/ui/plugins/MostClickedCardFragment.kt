package de.thkoeln.colab.fearlesschange.ui.plugins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.nonNullObserve
import de.thkoeln.colab.fearlesschange.ui.BasicPatternFragment
import de.thkoeln.colab.fearlesschange.ui.pattern.preview.PatternPreviewAdapter
import kotlinx.android.synthetic.main.most_clicked_card_fragment.*

class MostClickedCardFragment : BasicPatternFragment<MostClickedCardViewModel>() {

    companion object {
        fun newInstance() = MostClickedCardFragment()
    }

    private val cardPreviewAdapter = PatternPreviewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.most_clicked_card_fragment, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cardPreviewAdapter.patternClickedListener = viewModel.patternCardClicked
        cardPreviewAdapter.inflate(most_clicked_pattern_card_container, true)
        viewModel.mostClickedPattern.nonNullObserve(this) { cardPreviewAdapter.bind(it) }
    }


    override fun createViewModel() = ViewModelProviders.of(this).get(MostClickedCardViewModel::class.java)
}
