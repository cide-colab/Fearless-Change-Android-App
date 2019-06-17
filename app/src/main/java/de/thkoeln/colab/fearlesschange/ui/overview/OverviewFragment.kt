package de.thkoeln.colab.fearlesschange.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.BasicPatternFragment
import kotlinx.android.synthetic.main.activity_overview.*


class OverviewFragment : BasicPatternFragment<OverviewViewModel>() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pattern_cards_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = OverviewRecyclerViewAdapter()
        pattern_cards_recycler_view.adapter = adapter

        viewModel.pattern.observe(this) { adapter.setItems(it) }

        adapter.patternClickedListener = { viewModel.patternCardClicked(it) }

    }

    override fun createViewModel() = ViewModelProviders.of(this).get(OverviewViewModel::class.java)
}
