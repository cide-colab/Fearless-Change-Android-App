package de.thkoeln.colab.fearlesschange.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewHolder
import de.thkoeln.colab.fearlesschange.ui.pattern.preview.PatternPreviewAdapter
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*

class FavoritesSwipeToDeleteAdapter(context: Context, private val deleteText: String) : SwipeToDeleteRecyclerViewAdapter<PatternInfo, FavoritesSwipeToDeleteAdapter.FavoritesViewHolder>(context) {

    var patternClickedListener: (patternInfo: PatternInfo) -> Unit = {}

    override fun getSnackbarText(name: String) = "$name $deleteText"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return FavoritesViewHolder(view, patternClickedListener)
    }


    class FavoritesViewHolder(view: View, patternClickedListener: (patternInfo: PatternInfo) -> Unit) : SwipeToDeleteRecyclerViewHolder<PatternInfo>(view) {

        private val adapter = PatternPreviewAdapter(patternClickedListener).apply {
            inflate(view.swipe_to_delete_container, true)
        }

        override fun bind(item: PatternInfo) {
            adapter.bind(item)
        }

        override fun getDisplayName(item: PatternInfo) = item.pattern.title
        override fun getForeground() = itemView.swipe_to_delete_container
    }
}