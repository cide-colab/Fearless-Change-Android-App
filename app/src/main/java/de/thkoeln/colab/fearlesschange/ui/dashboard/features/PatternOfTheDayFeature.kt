package de.thkoeln.colab.fearlesschange.ui.dashboard.features


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.colab.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import kotlinx.android.synthetic.main.feature_pattern_of_the_day.*


class PatternOfTheDayFeature : Fragment() {

    private val cardPreviewAdapter = PatternCardPreviewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.feature_pattern_of_the_day, container, false)

    override fun onStart() {
        super.onStart()
        val viewModel = ViewModelProviders.of(activity!!).get(PatternViewModel::class.java)
        viewModel.patternOfTheDay.nonNullObserve(this) { onPatternInfoUpdate(it) }

        cardPreviewAdapter.onCardClickedListener = {
            val patternId = it?.pattern?.id
            viewModel.cardPreviewClicked(listOfNotNull(patternId).toLongArray(), patternId)
        }
        card_preview.setAdapter(cardPreviewAdapter)
    }

    private fun onPatternInfoUpdate(patternInfo: PatternInfo?) {
        cardPreviewAdapter.change(patternInfo)
    }

}


