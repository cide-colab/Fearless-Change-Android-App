package de.thkoeln.colab.fearlesschange.view.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.view.pattern.preview.PatternPreviewViewHolder
import kotlinx.android.synthetic.main.pattern_preview.view.*
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*

class FavoritesSwipeToDeleteAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<PatternInfo, FavoritesSwipeToDeleteAdapter.FavoritesViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return FavoritesViewHolder(view)
    }


    class FavoritesViewHolder(view: View) : SwipeToDeleteRecyclerViewHolder<PatternInfo>(view) {

        private val adapter = PatternPreviewViewHolder().apply {
            inflate(view.swipe_to_delete_container, true)
        }

        override fun bind(item: PatternInfo) {
            adapter.bind(item)
        }

        override fun getClickableView() = itemView.pattern_preview_card

        override fun getForeground() = itemView.swipe_to_delete_container
    }
}