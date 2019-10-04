package de.thkoeln.colab.fearlesschange.view.label

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.persistance.label.Label

class LabelAutocompleteAdapter(context: Context, labels: List<Label> = listOf()) : ArrayAdapter<Label>(context, 0, labels) {
    private val filterLabels: MutableList<Label>
    private var allLabels: List<Label>

    init {
        this.filterLabels = ArrayList(labels)
        this.allLabels = ArrayList(labels)
    }

    override fun getCount(): Int {
        return filterLabels.size
    }

    override fun getItem(position: Int): Label? {
        return filterLabels[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateLabels(labels: List<Label>) {
        this.allLabels = labels
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = ((convertView ?: let {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        }) as TextView).apply {
            setTextColor(Color.BLACK)
        }


        getItem(position)?.let { labels ->
            view.text = labels.name
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any): String {
                return (resultValue as Label).name
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults = FilterResults().apply {
                constraint.let { filter ->
                    val filtered = allLabels.filter { it.name.startsWith(filter.toString(), true) }
                    values = filtered
                    count = filtered.size
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterLabels.clear()
                filterLabels.addAll(((results?.values
                        ?: listOf<Label>()) as List<*>).filterIsInstance<Label>())
                notifyDataSetChanged()
            }
        }
    }
}