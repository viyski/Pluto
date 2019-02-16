package com.alien.baselib.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alien.baselib.ExtendFun.dp2px
import com.alien.baselib.R
import com.bumptech.glide.Glide

open class IconTabView: LinearLayout {

    lateinit var textView: TextView
    lateinit var imageView: ImageView
    lateinit var params: LinearLayout.LayoutParams
    private var textColor: Int = context.resources.getColor(R.color.color_333)
    private var textSize: Float = 16f
    private var imageResId: Int = 0
    private var text: String = ""

    constructor(context: Context?): super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        val t = context.obtainStyledAttributes(attrs, R.styleable.IconTabView)
        textColor = t.getColor(R.styleable.IconTabView_icon_tab_text_color, context.resources.getColor(R.color.color_333))
        textSize = t.getDimension(R.styleable.IconTabView_icon_tab_text_size, 12f)
        imageResId = t.getResourceId(R.styleable.IconTabView_icon_tab_image, 0)
        text = t.getString(R.styleable.IconTabView_icon_tab_text)
        t.recycle()

        init(context)
    }

    private fun init(context: Context?) {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setBackgroundResource(R.drawable.bg_list_item)

        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.weight = 1f
        layoutParams = lp

        textView = TextView(context)
        imageView = ImageView(context)

        val padding = dp2px(12f)

        textView.setTextColor(textColor)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textView.text = text
        textView.setPadding(0, 0, 0, padding)
        textView.gravity = Gravity.CENTER
        imageView.setImageResource(imageResId)

        params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val p = LinearLayout.LayoutParams(dp2px(44f), dp2px(44f))
        p.gravity = Gravity.CENTER

        addView(imageView, p)
        addView(textView, this.params)
    }

    fun setImageLayoutParams(params: LinearLayout.LayoutParams) {
        imageView.layoutParams = params
    }

    fun setTextSize(textSize: Float) {
        textView.textSize = textSize
    }

    fun setTextColor(color: Int) {
        textView.setTextColor(color)
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun setImageResource(resId: Int) {
        imageView.setImageResource(resId)
    }

    fun loadImage(url: String) {
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .into(imageView)
    }

}
