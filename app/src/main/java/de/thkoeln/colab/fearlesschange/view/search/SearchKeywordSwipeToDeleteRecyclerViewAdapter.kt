package de.thkoeln.colab.fearlesschange.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.keyword.Keyword
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*


class SearchKeywordSwipeToDeleteRecyclerViewAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Keyword, SearchKeywordSwipeToDeleteRecyclerViewAdapter.KeywordListViewHolder>(context) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return KeywordListViewHolder(view)
    }

    class KeywordListViewHolder(itemView: View) : SwipeToDeleteRecyclerViewHolder<Keyword>(itemView) {
        private val adapter = KeywordViewHolder().apply {
            inflate(itemView.swipe_to_delete_container, true)
        }

        override fun bind(item: Keyword) {
            adapter.bind(item)
        }

        override fun getForeground(): View = itemView.swipe_to_delete_container
    }
}