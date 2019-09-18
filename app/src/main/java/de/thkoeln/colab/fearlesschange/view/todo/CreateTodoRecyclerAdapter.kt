package de.thkoeln.colab.fearlesschange.view.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.create_checkbox_item.view.*

class CreateTodoRecyclerAdapter : RecyclerViewAdapter<Todo, CreateTodoRecyclerAdapter.CreateCheckboxViewHolder>(true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateCheckboxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.create_checkbox_item, parent, false)
        return CreateCheckboxViewHolder(view)
    }

    class CreateCheckboxViewHolder(itemView: View) : ViewHolder<Todo>(itemView) {

        private var onTextChange: (text: String) -> Unit = {}

        init {
            itemView.create_checkbox_text.addTextChangedListener { t -> onTextChange(t.toString()) }
        }

        override fun bind(item: Todo) {
            onTextChange = { item.text = it }
            itemView.create_checkbox_checkbox.setOnCheckedChangeListener { _, isChecked -> item.state = isChecked }
            itemView.create_checkbox_checkbox.isChecked = item.state
            itemView.create_checkbox_text.setText(item.text)
        }
    }
}