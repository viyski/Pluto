package com.alien.baselib.widget

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.alien.baselib.R
import com.alien.baselib.support.LineItemDecoration

open class SwipeRefresh: FrameLayout {

    private var swipeRefreshLayout: SwipeRefreshLayout = SwipeRefreshLayout(context)
    private var recyclerView: RecyclerView = RecyclerView(context)
    var hasMore: Boolean = true

    constructor(context: Context): super(context)

    constructor(context: Context, @Nullable attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, @Nullable attrs: AttributeSet, @AttrRes defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(LineItemDecoration(0,1, ContextCompat.getColor(context,R.color.divider)))
        swipeRefreshLayout.addView(recyclerView, ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(swipeRefreshLayout, params)
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration) = recyclerView.addItemDecoration(decor)

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    fun removeItemDecoration(){
        if (recyclerView.itemDecorationCount > 0)
            recyclerView.removeItemDecorationAt(0)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>){
        recyclerView.adapter = adapter
    }

    fun refreshFinish() {
        swipeRefreshLayout.isRefreshing = false
    }

    fun setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener){
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
    }

    fun setOnMoreListener(onMoreListener: SwipeRefreshLayout.OnRefreshListener){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            var lastVisibleItemPosition: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (hasMore && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= recyclerView.adapter?.itemCount!! - 1){
                    onMoreListener.onRefresh()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.layoutManager is LinearLayoutManager) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                }
            }
        })
    }
}