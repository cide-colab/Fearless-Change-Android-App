package de.thkoeln.colab.fearlesschange.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.Keyword
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewHolder
import kotlinx.android.synthetic.main.keyword_grid_item.view.*

class SearchKeywordRecyclerAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Keyword, SearchKeywordRecyclerAdapter.KeywordListViewHolder>(context) {

    var keywordClickedListener: (Keyword) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.keyword_grid_item, parent, false)
        return KeywordListViewHolder(view, keywordClickedListener)
    }

    class KeywordListViewHolder(itemView: View, private val keywordClickedListener: (Keyword) -> Unit) : SwipeToDeleteRecyclerViewHolder<Keyword>(itemView) {
        override fun bind(item: Keyword) {
            itemView.keyword_list_item_textview.text = item.keyword
            itemView.keyword_list_item_textview.setOnClickListener { keywordClickedListener(item) }
        }

        override fun getDisplayName(item: Keyword) = item.keyword

        override fun getForeground(): View = itemView.keyword_list_item_textview
    }
}