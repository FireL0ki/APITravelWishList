package com.example.apitravelwishlist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface OnDataChangedListener {
//    fun onListItemMoved(from: Int, to: Int)  // move up and down on list
    fun onListItemDeleted(position: Int)
}

class OnListItemSwipeListener(private val onDataChangedListener: OnDataChangedListener):
    ItemTouchHelper.SimpleCallback(
    0, // no reordering
    ItemTouchHelper.RIGHT // to delete
) {
    override fun onMove( // moving up and down
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
//        val fromPosition = viewHolder.adapterPosition // where in the list
//        val toPosition = target.adapterPosition // where was it moved to?
//        onDataChangedListener.onListItemMoved(fromPosition, toPosition)
        return true // is the move permitted?
    }

    // swipe left and right
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.RIGHT) {
            onDataChangedListener.onListItemDeleted(viewHolder.adapterPosition)
        }
    }


}