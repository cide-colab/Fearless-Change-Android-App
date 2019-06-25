package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.nonNullObserve
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import de.thkoeln.colab.fearlesschange.view.pattern.preview.PatternPreviewViewHolder
import kotlinx.android.synthetic.main.most_clicked_card_fragment.*

class MostClickedCardFragment : PatternViewModelFragment<MostClickedCardViewModel>() {

    companion object {
        fun newInstance() = MostClickedCardFragment()
    }

    private val cardPreviewAdapter = PatternPreviewViewHolder()

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
