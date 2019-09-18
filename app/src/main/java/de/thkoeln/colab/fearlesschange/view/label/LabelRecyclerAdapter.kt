package de.thkoeln.colab.fearlesschange.view.label

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import kotlinx.android.synthetic.main.create_label_item.view.*

class LabelRecyclerAdapter : RecyclerViewAdapter<Label, LabelRecyclerAdapter.LabelViewHolder>(true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        return LabelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.create_label_item, parent, false))
    }

    class LabelViewHolder(itemView: View) : ViewHolder<Label>(itemView) {
        override fun bind(item: Label) {
            itemView.label_item.color = item.color
            itemView.label_item.name = item.name
        }
    }
}