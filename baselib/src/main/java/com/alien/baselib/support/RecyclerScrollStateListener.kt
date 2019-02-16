package com.alien.baselib.support

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class RecyclerScrollStateListener: RecyclerView.OnScrollListener() {

    private var lastVisibilityPosition: Int = 0


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (!getData().isEnd && !getData().isLoading && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibilityPosition >= getAdapterLast(recyclerView)){
            onNextLoad()
        }
    }

    private fun getAdapterLast(recyclerView: RecyclerView): Int {
        return when {
            recyclerView.layoutManager is GridLayoutManager -> {
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                recyclerView.adapter?.itemCount?.minus(gridLayoutManager.spanCount)?.minus(getData().footerCount)!!
            }
            recyclerView.layoutManager is StaggeredGridLayoutManager -> {
                val staggeredGridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                recyclerView.adapter?.itemCount?.minus(staggeredGridLayoutManager.spanCount)?.minus(getData().footerCount)!!
            }
            else -> {
                recyclerView.adapter?.itemCount?.minus(1)?.minus(getData().footerCount)!!
            }
        }

    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        when{
            recyclerView.layoutManager is LinearLayoutManager -> {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                lastVisibilityPosition = linearLayoutManager.findLastVisibleItemPosition()
            }
            recyclerView.layoutManager is StaggeredGridLayoutManager -> {
                val staggeredGridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                lastVisibilityPosition = findMax(staggeredGridLayoutManager.findLastVisibleItemPositions(intArrayOf()))
            }
            recyclerView.layoutManager is GridLayoutManager -> {
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                lastVisibilityPosition = gridLayoutManager.findLastVisibleItemPosition()
            }
            else ->{

            }
        }
    }

    private fun findMax(indexs: IntArray?): Int {
        var index = 0
        for(position in indexs!!){
            if (position > index)
                index = position
        }
        return index
    }

    abstract fun getData(): StateData

    abstract fun onNextLoad()

    data class StateData(val footerCount: Int, var isEnd: Boolean = false, var isLoading: Boolean = false)
}