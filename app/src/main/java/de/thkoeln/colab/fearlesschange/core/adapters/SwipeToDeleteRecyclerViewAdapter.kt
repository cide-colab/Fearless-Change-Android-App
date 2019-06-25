/*
 * Developed by Herborn-Software on 6/13/19 1:09 AM.
 * Last modified 6/13/19 1:09 AM.
 * Copyright (c) 2019.  All rights reserved.
 */

package de.thkoeln.colab.fearlesschange.core.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.core.onTimeout


abstract class SwipeToDeleteRecyclerViewAdapter<T, VH : SwipeToDeleteRecyclerViewAdapter.SwipeToDeleteRecyclerViewHolder<T>>(val context: Context) : AdvancedRecyclerViewAdapter<T, VH>(),
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener<VH> {

    var onDeleteItemAcceptedListener: (item: T) -> Unit = {}
    var onDeleteItemListener: (item: T) -> Unit = {}
    var onRestoreItemListener: (item: T) -> Unit = {}
    var onDeleteSnackBarText: ((item: T) -> String)? = null
    var onDeleteUndoActionText: (item: T) -> String = { "UNDO" }

    private fun restoreItem(item: T, position: Int) {
        addItem(item, position)
        onRestoreItemListener(item)
    }

    override fun onSwiped(viewHolder: VH, direction: Int, position: Int) {
        // backup of removed item for undo purpose
        val deletedItem = items[viewHolder.adapterPosition]
        val deletedIndex = viewHolder.adapterPosition

        // remove the item from recycler view
        removeItem(viewHolder.adapterPosition)
        onDeleteItemListener(deletedItem)
        onDeleteSnackBarText?.invoke(deletedItem)?.let {
            Snackbar.make(viewHolder.itemView, it, Snackbar.LENGTH_LONG).apply {
                setAction(onDeleteUndoActionText(deletedItem)) { restoreItem(deletedItem, deletedIndex) }
                setActionTextColor(Color.YELLOW)
                onTimeout { onDeleteItemAcceptedListener(deletedItem) }
            }.show()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        super.onAttachedToRecyclerView(recyclerView)
        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    abstract class SwipeToDeleteRecyclerViewHolder<T>(itemView: View) : ViewHolder<T>(itemView), ForegroundHolder

    interface ForegroundHolder {
        fun getForeground(): View
    }


}


