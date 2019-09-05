package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.CheckboxData
import kotlinx.android.synthetic.main.create_checkbox_item.view.*
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*

class CreateCheckboxRecyclerAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<CheckboxData, CreateCheckboxRecyclerAdapter.CreateCheckboxViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateCheckboxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return CreateCheckboxViewHolder(view)
    }

    class CreateCheckboxViewHolder(itemView: View) : SwipeToDeleteRecyclerViewHolder<CheckboxData>(itemView) {
        val view: View = LayoutInflater.from(itemView.context).inflate(R.layout.create_checkbox_item, itemView.swipe_to_delete_container, true).apply {
            create_checkbox_checkbox.setOnCheckedChangeListener { _, isChecked -> currentItem?.state = isChecked }
            create_checkbox_text.addTextChangedListener { t -> currentItem?.text = t.toString() }
        }

        private var currentItem: CheckboxData? = null

        override fun bind(item: CheckboxData) {
            currentItem = item
            view.create_checkbox_checkbox.isChecked = item.state
            view.create_checkbox_text.setText(item.text)
        }

        override fun getForeground() = itemView.swipe_to_delete_container
    }
}