package de.thkoeln.colab.fearlesschange.core.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<T, VH : RecyclerViewAdapter.ViewHolder<T>> : RecyclerView.Adapter<VH>() {

    protected val items = mutableListOf<T>()
    var onItemClickedListener: (item: T) -> Unit = {}

    fun setItems(items: List<T>) {
        val prevCount = itemCount
        this.items.clear()
        notifyItemRangeRemoved(0, prevCount)
        items.forEach { addItem(it) }
    }

    fun setItemsNotEquals(items: List<T>) {
        items.forEachIndexed { index, item ->
            if (this.items.getOrNull(index) != item) {
                addItem(item, index)
            }
        }
        while (this.items.size > items.size) {
            removeLastItem()
        }
    }

    fun removeLastItem() {
        removeItem(this.items.lastIndex)
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: T, position: Int) {
        this.items.add(position, item)
        notifyItemInserted(position)
    }

    fun addItem(item: T) {
        addItem(item, this.items.size)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.notifyItemClicked = onItemClickedListener
//        onItemClickedListener?.let { holder.getClickableView().setOnClickListener { it(item) } }

    }

    abstract class ViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        internal var notifyItemClicked: (item: T) -> Unit = {}
        abstract fun bind(item: T)
//        open fun getClickableView() = itemView
    }

}
