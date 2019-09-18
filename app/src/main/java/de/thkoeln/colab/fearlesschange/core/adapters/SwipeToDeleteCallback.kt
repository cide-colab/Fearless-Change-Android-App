package de.thkoeln.colab.fearlesschange.core.adapters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.toPx

interface SwipeToDeleteAdapter {
    fun onDelete(position: Int)
}

class SwipeToDeleteCallback(private val adapter: SwipeToDeleteAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT/* or ItemTouchHelper.RIGHT*/) {

    private val red = Color.argb(50, 206, 14, 45)
    private val background: Drawable = ColorDrawable(red)
    private val backgroundCornerOffset = 20

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        getDefaultUIUtil().onSelected(viewHolder?.itemView)
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        getDefaultUIUtil().onDrawOver(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        getDefaultUIUtil().clearView(viewHolder.itemView)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val icon: Drawable = viewHolder.itemView.context.getDrawable(R.drawable.ic_delete_sweep_black_24dp)
                ?: ColorDrawable(red)
        val iconMargin = 2.toPx()//(itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        when {
            dX > 0 -> { // Swiping to the right
                val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
                val iconRight = itemView.left + iconMargin
                val iconBounds = Rect(iconLeft, iconTop, iconRight, iconBottom)
                icon.bounds = iconBounds

                val backgroundBounds = Rect(itemView.left, itemView.top, itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom)
                background.bounds = backgroundBounds
                background.draw(c)
                icon.draw(c)
            }
            dX < 0 -> { // Swiping to the left
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                val iconBounds = Rect(iconLeft, iconTop, iconRight, iconBottom)
                icon.bounds = iconBounds

                val backgroundBounds = Rect(itemView.right + dX.toInt() - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom)
                background.bounds = backgroundBounds
                background.draw(c)
                icon.draw(c)
            }
            else -> {
                // view is unSwiped
//                val backgroundBounds = Rect(0,0,0,0)
//                background.bounds = backgroundBounds
            }
        }
        getDefaultUIUtil().onDraw(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onDelete(viewHolder.adapterPosition)
    }

//    interface SwipeListener<T> {
//        fun onSwiped(viewHolder: T, direction: Int, position: Int)
//    }
//
//    abstract fun getForeground(): View
//
//    fun withForeground(viewHolder: RecyclerView.ViewHolder?, block: (View) -> Unit) {
//        if (viewHolder is SwipeToDeleteRecyclerViewAdapter.ForegroundHolder) block(viewHolder.getForeground())
//    }
}