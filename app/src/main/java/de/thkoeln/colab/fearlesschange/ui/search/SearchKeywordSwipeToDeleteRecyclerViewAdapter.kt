package de.thkoeln.colab.fearlesschange.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.Keyword
import de.thkoeln.colab.fearlesschange.ui.LayoutAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewHolder
import kotlinx.android.synthetic.main.filter_item.view.*
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*


class KeywordAdapter : LayoutAdapter<Keyword>(R.layout.filter_item) {

    override fun bind(view: View, value: Keyword) {
        view.filter_item_text.text = value.keyword
    }
}

class SearchKeywordSwipeToDeleteRecyclerViewAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Keyword, SearchKeywordSwipeToDeleteRecyclerViewAdapter.KeywordListViewHolder>(context) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return KeywordListViewHolder(view)
    }

    class KeywordListViewHolder(itemView: View) : SwipeToDeleteRecyclerViewHolder<Keyword>(itemView) {
        private val adapter = KeywordAdapter().apply {
            inflate(itemView.swipe_to_delete_container, true)
        }

        override fun bind(item: Keyword) {
            adapter.bind(item)
        }

        override fun getForeground(): View = itemView.swipe_to_delete_container
    }
}