package com.example.kotlinproject1_todolist.Decoration

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerVIewItemDecoration(val size10: Int, val size5: Int) : RecyclerView.ItemDecoration() {

    fun dpToPx(context : Context, dp : Int): Int {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),context.resources.displayMetrics).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position : Int = parent.getChildAdapterPosition(view)
        val itemCount : Int = state.itemCount

        if(position == 0 || position == 1){
            outRect.top = size10
            outRect.bottom = size10
        } else {
            outRect.bottom = size10
        }

        val lp : GridLayoutManager.LayoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex : Int = lp.spanIndex

        if(spanIndex == 0){
            outRect.left = size10
            outRect.right = size5
        } else if(spanIndex == 1){
            outRect.left = size5
            outRect.right = size10
        }
    }
}