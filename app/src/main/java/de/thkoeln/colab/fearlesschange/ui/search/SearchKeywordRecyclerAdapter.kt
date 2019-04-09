package de.thkoeln.colab.fearlesschange.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.Keyword

class SearchKeywordRecyclerAdapter(var keywords: List<Keyword> = mutableListOf()) :
        RecyclerView.Adapter<SearchKeywordRecyclerAdapter.KeywordListViewHolder>() {
    var keywordClickedListener: ((Keyword) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_keyword_list_item, parent, false)
        return KeywordListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return keywords.size
    }

    override fun onBindViewHolder(holder: KeywordListViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

    fun updateKeywords(keywords: List<Keyword>) {
        this.keywords = keywords
        notifyDataSetChanged()
    }

    inner class KeywordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.keyword_list_item_textview)
        fun bind (keyword: Keyword) {
            textView.text = keyword.keyword
            textView.setOnClickListener { keywordClickedListener?.invoke(keyword) }
        }
    }
}