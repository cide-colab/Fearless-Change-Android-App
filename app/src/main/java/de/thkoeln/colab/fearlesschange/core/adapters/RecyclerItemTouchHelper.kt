package de.thkoeln.colab.fearlesschange.core.adapters

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemTouchHelper<T : SwipeToDeleteRecyclerViewAdapter.ForegroundHolder>(dragDirs: Int, swipeDirs: Int, private val listener: RecyclerItemTouchHelperListener<T>) :
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
        if (viewHolder is SwipeToDeleteRecyclerViewAdapter.ForegroundHolder) block(viewHolder.getForeground())
    }
}