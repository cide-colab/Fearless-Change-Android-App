package de.thkoeln.colab.fearlesschange.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AdvancedRecyclerViewAdapter<T, VH : AdvancedRecyclerViewAdapter.ViewHolder<T>> : RecyclerView.Adapter<VH>() {

    protected val items = mutableListOf<T>()
    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    abstract class ViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

}
