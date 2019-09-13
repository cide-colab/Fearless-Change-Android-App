package de.thkoeln.colab.fearlesschange.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.core.getDrawableId
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import kotlinx.android.synthetic.main.pattern_preview.view.*

class SearchPatternRecyclerViewAdapter : RecyclerViewAdapter<PatternInfo, SearchPatternRecyclerViewAdapter.OverviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            OverviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pattern_preview, parent, false))

    class OverviewViewHolder(view: View) : RecyclerViewAdapter.ViewHolder<PatternInfo>(view) {

        override fun bind(item: PatternInfo) {
            with(item.pattern) {
                val picture = itemView.context?.getDrawableId(pictureName)
                        ?: R.drawable.default_pattern_image
                itemView.pattern_preview_image.setImageResource(picture)
                itemView.pattern_preview_title.text = title
                itemView.pattern_preview_summary.text = summary
                itemView.pattern_preview_favorite_icon.visibility = if (favorite) VISIBLE else GONE
                itemView.pattern_preview_notes_count.text = item.noteCount.toString()
                itemView.pattern_preview_notes_count.visibility = if (item.noteCount > 0) VISIBLE else GONE
                itemView.pattern_preview_notes_icon.visibility = if (item.noteCount > 0) VISIBLE else GONE
                itemView.pattern_preview_card.setOnClickListener { notifyItemClicked(item) }
            }
        }

    }
}