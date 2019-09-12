package de.thkoeln.colab.fearlesschange.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.keyword.Keyword
import kotlinx.android.synthetic.main.filter_item.view.*


class SearchKeywordSwipeToDeleteRecyclerViewAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Keyword, SearchKeywordSwipeToDeleteRecyclerViewAdapter.KeywordListViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            KeywordListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false))

    class KeywordListViewHolder(itemView: View) : ViewHolder<Keyword>(itemView) {
        override fun bind(item: Keyword) {
            itemView.filter_item_text.text = item.keyword
        }
    }
}