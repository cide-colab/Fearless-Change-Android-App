package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import kotlinx.android.synthetic.main.most_clicked_card_fragment.*

class MostClickedCardFragment : InteractiveFragment<MostClickedCardViewModel>() {

    companion object {
        fun newInstance() = MostClickedCardFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.most_clicked_card_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        most_clicked_card_pattern_card.setOnClickListener { viewModel.patternCardClicked(most_clicked_card_pattern_card.patternPreviewData) }
        viewModel.mostClickedPatternData.nonNullObserve(this) { most_clicked_card_pattern_card.patternPreviewData = it }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(MostClickedCardViewModel::class.java)
}
