package de.thkoeln.colab.fearlesschange.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import kotlinx.android.synthetic.main.pattern_card_preview_item.view.*

class PatternCardPreviewRecyclerAdapter(swipeToDeleteEnabled: Boolean = false) : RecyclerViewAdapter<PatternPreviewData, PatternCardPreviewRecyclerAdapter.OverviewViewHolder>(swipeToDeleteEnabled) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            OverviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pattern_card_preview_item, parent, false))

    class OverviewViewHolder(view: View) : RecyclerViewAdapter.ViewHolder<PatternPreviewData>(view) {
        override fun bind(item: PatternPreviewData) {
            itemView.pattern_card_preview_item_pattern_card.patternPreviewData = item
            itemView.pattern_card_preview_item_pattern_card.setOnClickListener { notifyItemClicked(item) }
        }
    }
}