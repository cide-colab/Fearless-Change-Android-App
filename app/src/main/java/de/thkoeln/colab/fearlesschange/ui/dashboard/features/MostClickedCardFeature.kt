package de.thkoeln.colab.fearlesschange.ui.dashboard.features


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.colab.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import kotlinx.android.synthetic.main.feature_most_clicked_card.*


class MostClickedCardFeature : Fragment() {

    private lateinit var cardPreviewAdapter: PatternCardPreviewAdapter
    private lateinit var viewModel: PatternViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.feature_most_clicked_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(PatternViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        viewModel.mostClickedPattern.observe(this, Observer { onCardUpdate(it) })
        cardPreviewAdapter = PatternCardPreviewAdapter()
        cardPreviewAdapter.onCardClickedListener = {
            val patternId = it?.pattern?.id
            viewModel.cardPreviewClicked(listOfNotNull(patternId).toLongArray(), patternId)
        }
        card_preview.setAdapter(cardPreviewAdapter)
    }

    private fun onCardUpdate(patternInfo: PatternInfo?) {
        cardPreviewAdapter.change(patternInfo)
    }
}
