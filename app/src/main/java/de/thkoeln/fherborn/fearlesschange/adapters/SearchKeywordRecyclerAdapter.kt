package de.thkoeln.fherborn.fearlesschange.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword

class SearchKeywordRecyclerAdapter(var keywords: MutableList<Keyword> = mutableListOf()) :
        RecyclerView.Adapter<SearchKeywordRecyclerAdapter.KeywordListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_keyword_list_item, parent, false)
        return KeywordListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return keywords.size
    }

    override fun onBindViewHolder(holder: KeywordListViewHolder, position: Int) {
        holder.bind(keywords[position].keyword)
    }

    inner class KeywordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.keyword_list_item_textview)
        fun bind (keyword: String) {
            textView.text = keyword
        }
    }
}