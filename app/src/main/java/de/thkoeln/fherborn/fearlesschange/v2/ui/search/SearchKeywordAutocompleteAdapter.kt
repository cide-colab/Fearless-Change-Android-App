package de.thkoeln.fherborn.fearlesschange.v2.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.Keyword
import android.view.LayoutInflater


class SearchKeywordAutocompleteAdapter(context: Context, var keywords: List<Keyword> = emptyList())
    : ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, keywords.map{ k -> k.keyword}) {

    var keywordClickedListener: ((Keyword) -> Unit)? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setOnClickListener {  }
//        view.text = keywords[position].keyword

//        val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = vi.inflate(android.R.layout.simple_dropdown_item_1line, parent, false) as TextView
//        val holder = convertView
//                ?.let { it.tag as KeywordAutocompleteViewHolder }
//                ?: let {
//                    val h = KeywordAutocompleteViewHolder(v)
//                    v.tag = h
//                    h
//                }

//        holder.bind(keywords[position])
        Log.e("asd", "getView called")
        return view
    }
    fun updateKeywords(keywords: List<Keyword>) {
        this.keywords = keywords
        notifyDataSetChanged()
    }
    inner class KeywordAutocompleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.keyword_auto_complete_item_textview)
        fun bind(keyword: Keyword) {
            textView.text = keyword.keyword
            textView.setOnClickListener { keywordClickedListener?.invoke(keyword) }
        }
    }

}