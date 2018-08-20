package de.thkoeln.fherborn.fearlesschange.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword

class SearchKeywordRecyclerAdapter(var keywords: List<Keyword> = listOf()) :
        RecyclerView.Adapter<SearchKeywordRecyclerAdapter.KeywordListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordListViewHolder {
        return KeywordListViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int {
        return keywords.size
    }

    override fun onBindViewHolder(holder: KeywordListViewHolder, position: Int) {
        holder.textView.text = keywords[position].keyword
    }

    inner class KeywordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.selected_keywords)
    }
}