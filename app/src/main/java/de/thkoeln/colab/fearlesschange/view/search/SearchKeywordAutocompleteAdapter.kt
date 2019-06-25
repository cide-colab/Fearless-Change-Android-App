package de.thkoeln.colab.fearlesschange.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.persistance.keyword.Keyword


class SearchKeywordAutocompleteAdapter(context: Context, keywords: List<Keyword> = listOf()) : ArrayAdapter<Keyword>(context, 0, keywords) {
    private val filteredKeywords: MutableList<Keyword>
    private var allKeywords: List<Keyword>

    init {
        this.filteredKeywords = ArrayList(keywords)
        this.allKeywords = ArrayList(keywords)
    }

    override fun getCount(): Int {
        return filteredKeywords.size
    }

    override fun getItem(position: Int): Keyword? {
        return filteredKeywords[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateKeywords(keywords: List<Keyword>) {
        this.allKeywords = keywords
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = (convertView ?: let {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        }) as TextView


        getItem(position)?.let {keyword ->
            view.text = keyword.keyword
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any): String {
                return (resultValue as Keyword).keyword
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults = FilterResults().apply {
                constraint?.let {filter ->
                    val filtered = allKeywords.filter { it.keyword.toLowerCase().startsWith(filter.toString().toLowerCase()) }
                    values = filtered
                    count = filtered.size
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredKeywords.clear()
                (results?.values as? List<*>)?.filter { it is Keyword }?.forEach { filteredKeywords.add(it as Keyword) }
                notifyDataSetChanged()
            }
        }
    }
}