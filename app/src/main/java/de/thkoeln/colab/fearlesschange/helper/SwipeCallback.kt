package de.thkoeln.colab.fearlesschange.helper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by florianherborn on 23.08.18.
 */
class SwipeCallback(swipeDirs: Int, private val onSwipe: (RecyclerView.ViewHolder?, Int) -> Unit): ItemTouchHelper.SimpleCallback(0, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipe(viewHolder, direction)
    }

}