package de.thkoeln.colab.fearlesschange.view.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.observe
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.view.PatternCardPreviewRecyclerAdapter
import de.thkoeln.colab.fearlesschange.view.custom.MarginItemDecoration
import kotlinx.android.synthetic.main.pattern_cards_fragment.*


class CardsFragment : InteractiveFragment<CardsViewModel>() {

    companion object {
        fun newInstance() = CardsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pattern_cards_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PatternCardPreviewRecyclerAdapter()
        pattern_cards_recycler_view.adapter = adapter
        pattern_cards_recycler_view.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))

        viewModel.pattern.observe(this) { adapter.setItems(it) }
        adapter.onItemClickedListener = viewModel.patternCardClicked

    }

    override fun createViewModel() = ViewModelProviders.of(this).get(CardsViewModel::class.java)
}
