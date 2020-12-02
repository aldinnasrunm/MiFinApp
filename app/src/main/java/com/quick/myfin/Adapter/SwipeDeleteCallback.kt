package com.quick.myfin.Adapter

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeDeleteCallback(context: Context?) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        val itemView = viewHolder.itemView
//        val itemHeight = itemView.bottom - itemView.top
//        background.color = backgroundColor
//        background.setBounds(
//            itemView.right + dX.toInt(),
//            itemView.top,
//            itemView.right,
//            itemView.bottom
//        )
//        background.draw(canvas)
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//    }
}