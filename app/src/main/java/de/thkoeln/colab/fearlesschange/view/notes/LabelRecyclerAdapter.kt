package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import kotlinx.android.synthetic.main.label_item.view.*

class LabelRecyclerAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Label, LabelRecyclerAdapter.LabelViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        return LabelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.label_item, parent, false))
    }

    class LabelViewHolder(itemView: View) : ViewHolder<Label>(itemView) {
        override fun bind(item: Label) {
            itemView.label_item.color = item.color
            itemView.label_item.name = item.name
        }
    }
}