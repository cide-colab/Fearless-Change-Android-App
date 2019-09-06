/*
 * Developed by Herborn-Software on 6/13/19 1:09 AM.
 * Last modified 6/13/19 1:09 AM.
 * Copyright (c) 2019.  All rights reserved.
 */

package de.thkoeln.colab.fearlesschange.core.adapters

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


abstract class SwipeToDeleteRecyclerViewAdapter<T, VH : RecyclerViewAdapter.ViewHolder<T>>(val context: Context) :
        RecyclerViewAdapter<T, VH>(), SwipeToDeleteAdapter {

    var beforeDeleteItemListener: (item: T, position: Int) -> Boolean = { _, _ -> true }
    var afterDeleteItemListener: (item: T, position: Int) -> Unit = { _, _ -> }
    var afterRestoreItemListener: (item: T, position: Int) -> Unit = { _, _ -> }

    fun restoreItem(item: T, position: Int) {
        addItem(item, position)
        afterRestoreItemListener(item, position)
    }

    override fun onDelete(position: Int) {
        val item = items[position]
        if (beforeDeleteItemListener(item, position)) {
            removeItem(position)
            afterDeleteItemListener(item, position)
        }
    }

//    fun onSwiped(viewHolder: VH, direction: Int, position: Int) {
//        // backup of removed item for undo purpose
//        val deletedItem = items[viewHolder.adapterPosition]
//        val deletedIndex = viewHolder.adapterPosition
//
//        // remove the item from recycler view
//        removeItem(viewHolder.adapterPosition)
//        beforeDeleteItemListener(deletedItem)
//        onDeleteSnackBarText?.invoke(deletedItem)?.let {
//            Snackbar.make(viewHolder.itemView, it, Snackbar.LENGTH_LONG).apply {
//                setAction(onDeleteUndoActionText(deletedItem)) { restoreItem(deletedItem, deletedIndex) }
//                setActionTextColor(Color.YELLOW)
//                onTimeout { onDeleteItemAcceptedListener(deletedItem) }
//            }.show()
//        }
//    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = DefaultItemAnimator()
        ItemTouchHelper(SwipeToDeleteCallback(this, context)).attachToRecyclerView(recyclerView)
    }

}


