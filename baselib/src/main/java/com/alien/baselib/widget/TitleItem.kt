package com.alien.baselib.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alien.baselib.ExtendFun.dp2px
import com.alien.baselib.R

class TitleItem : LinearLayout{

    private var itemName: String? = null
    private var itemDes: String? = null
    private var itemNext = true
    private var itemIcon = 0

    private var iconIv: ImageView = ImageView(context)
    private var nextIv: ImageView = ImageView(context)
    private var nameTv: TextView = TextView(context)
    private var desTv: TextView = TextView(context)

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.TitleItem)
        itemName = ta.getString(R.styleable.TitleItem_item_name)
        itemDes = ta.getString(R.styleable.TitleItem_item_des)
        itemNext = ta.getBoolean(R.styleable.TitleItem_item_next, true)
        itemIcon = ta.getResourceId(R.styleable.TitleItem_item_icon, R.drawable.ic_next_right_gray_24dp)
        ta.recycle()
        init()
    }

    private fun init(){
        val params = LayoutParams(0, LayoutParams.WRAP_CONTENT)
        params.weight = 1f

        desTv.setPadding(dp2px(10f), 0, dp2px(10f), 0)

        setBackgroundResource(R.drawable.bg_list_item)
        nameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        desTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        setPadding(dp2px(12f), 0, dp2px(12f), 0)
        gravity = Gravity.CENTER_VERTICAL

        addView(iconIv)
        addView(nameTv, params)
        addView(desTv)
        addView(nextIv)

        nameTv.text = itemName
        desTv.text = itemDes
        nextIv.visibility = if (itemNext) View.VISIBLE else View.GONE
        nextIv.setImageResource(itemIcon)
    }

    fun setItemName(name: String) {
        nameTv.text = name
    }

    fun setItemDes(des: String){
        desTv.text = des
    }

    fun setItemIcon(@DrawableRes resId: Int){
        iconIv.setImageResource(resId)
    }

    fun setItemNext(next: Boolean){
        nextIv.visibility = if (next) View.VISIBLE else View.GONE
    }
}