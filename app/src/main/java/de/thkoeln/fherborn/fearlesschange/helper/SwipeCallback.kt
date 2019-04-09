package de.thkoeln.fherborn.fearlesschange.helper

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by florianherborn on 23.08.18.
 */
class SwipeCallback(swipeDirs: Int, private val onSwipe: (RecyclerView.ViewHolder?, Int) -> Unit): ItemTouchHelper.SimpleCallback(0, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipe(viewHolder, direction)
    }

}