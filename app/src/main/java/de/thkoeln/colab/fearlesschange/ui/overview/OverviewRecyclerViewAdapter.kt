package de.thkoeln.colab.fearlesschange.ui.overview

import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.ui.AdvancedRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.ui.pattern.preview.PatternPreviewAdapter

class OverviewRecyclerViewAdapter : AdvancedRecyclerViewAdapter<PatternInfo, OverviewRecyclerViewAdapter.OverviewViewHolder>() {

    var patternClickedListener: (patternInfo: PatternInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val adapter = PatternPreviewAdapter(patternClickedListener)
        return OverviewViewHolder(adapter.inflate(parent), adapter)
    }

    class OverviewViewHolder(view: View, private val adapter: PatternPreviewAdapter) : AdvancedRecyclerViewAdapter.ViewHolder<PatternInfo>(view) {
        override fun bind(item: PatternInfo) {
            adapter.bind(item)
        }
    }
}