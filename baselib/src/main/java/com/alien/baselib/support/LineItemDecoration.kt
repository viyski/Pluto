package com.alien.baselib.support

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class LineItemDecoration(var leftRight: Int, var topBottom: Int, @ColorInt var color: Int): RecyclerView.ItemDecoration() {

    private var mDrawable = ColorDrawable(color)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        var layoutManager: LinearLayoutManager? = null
        if (parent.layoutManager is LinearLayoutManager){
            layoutManager = parent.layoutManager as LinearLayoutManager
        }

        if (layoutManager == null || layoutManager.childCount == 0)
            return

        val childCount = parent.childCount

        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        var i = 0
        while (i < childCount - 1){
            val child = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                val center = (layoutManager.getTopDecorationHeight(child) - topBottom) / 2
                left = layoutManager.getLeftDecorationWidth(child)
                right = parent.width - layoutManager.getLeftDecorationWidth(child)
                top = child.bottom + params.bottomMargin + center
                bottom = top + topBottom;
                mDrawable.setBounds(left, top, right, bottom)
                mDrawable.draw(c)
            }else{
                val center = (layoutManager.getLeftDecorationWidth(child) - leftRight) / 2
                left = child.right + params.rightMargin + center
                right = left + leftRight
                top = layoutManager.getTopDecorationHeight(child)
                bottom = parent.height - layoutManager.getTopDecorationHeight(child)
                mDrawable.setBounds(left, top, right, bottom)
                mDrawable.draw(c)
            }
            i++
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(leftRight, topBottom, 0,0)
    }

}