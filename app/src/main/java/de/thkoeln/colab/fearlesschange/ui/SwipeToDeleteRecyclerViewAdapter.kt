/*
 * Developed by Herborn-Software on 6/13/19 1:09 AM.
 * Last modified 6/13/19 1:09 AM.
 * Copyright (c) 2019.  All rights reserved.
 */

package de.thkoeln.colab.fearlesschange.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.onTimeout


abstract class SwipeToDeleteRecyclerViewAdapter<T, VH : SwipeToDeleteRecyclerViewHolder<T>>(val context: Context) : AdvancedRecyclerViewAdapter<T, VH>(),
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
}

class RecyclerItemTouchHelper<T : ForegroundHolder>(dragDirs: Int, swipeDirs: Int, private val listener: RecyclerItemTouchHelperListener<T>) :
        ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        withForeground(viewHolder) { ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(it) }
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        withForeground(viewHolder) { ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, it, dX, dY, actionState, isCurrentlyActive) }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        withForeground(viewHolder) { ItemTouchHelper.Callback.getDefaultUIUtil().clearView(it) }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        withForeground(viewHolder) { ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, it, dX, dY, actionState, isCurrentlyActive) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder as T, direction, viewHolder.adapterPosition)
    }

    interface RecyclerItemTouchHelperListener<T> {
        fun onSwiped(viewHolder: T, direction: Int, position: Int)
    }

    fun withForeground(viewHolder: RecyclerView.ViewHolder?, block: (View) -> Unit) {
        if (viewHolder is ForegroundHolder) block(viewHolder.getForeground())
    }
}


interface ForegroundHolder {
    fun getForeground(): View
}

abstract class SwipeToDeleteRecyclerViewHolder<T>(itemView: View) : AdvancedRecyclerViewAdapter.ViewHolder<T>(itemView), ForegroundHolder