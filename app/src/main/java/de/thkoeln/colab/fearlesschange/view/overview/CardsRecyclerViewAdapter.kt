package de.thkoeln.colab.fearlesschange.view.overview

import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.core.adapters.AdvancedRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.view.pattern.preview.PatternPreviewViewHolder

class CardsRecyclerViewAdapter : AdvancedRecyclerViewAdapter<PatternInfo, CardsRecyclerViewAdapter.OverviewViewHolder>() {

    var patternClickedListener: (patternInfo: PatternInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val adapter = PatternPreviewViewHolder(patternClickedListener)
        return OverviewViewHolder(adapter.inflate(parent), adapter)
    }

    class OverviewViewHolder(view: View, private val adapter: PatternPreviewViewHolder) : AdvancedRecyclerViewAdapter.ViewHolder<PatternInfo>(view) {
        override fun bind(item: PatternInfo) {
            adapter.bind(item)
        }
    }
}