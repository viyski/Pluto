package com.alien.baselib

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by ReeseLuo on 2019/1/6.
 */
object ExtendFun {

    fun View.dp2px(dp: Float): Int =
            (this.context.resources.displayMetrics.density * dp + 0.5f).toInt()

    fun Activity.dp2px(dp: Float): Int =
            (this.resources.displayMetrics.density * dp + 0.5f).toInt()

    fun Fragment.toast(msg: String){
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    fun Context.toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.setupRecyclerView(recyclerView: RecyclerView,
                                   layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(recyclerView.context),
                                   adapter: RecyclerView.Adapter<BaseViewHolder>){
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    fun Activity.setupRecyclerView(recyclerView: RecyclerView,
                                   layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(recyclerView.context),
                                   adapter: RecyclerView.Adapter<BaseViewHolder>){
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}