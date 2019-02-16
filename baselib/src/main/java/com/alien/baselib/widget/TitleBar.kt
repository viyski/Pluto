package com.alien.baselib.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.alien.baselib.R

class TitleBar : FrameLayout {

    private var leftTv: TextView = TextView(context)
    private var centerTv: TextView = TextView(context)
    private var rightTv: TextView = TextView(context)
    private var rightIv: ImageView = ImageView(context)
    private val DEF_HEIGHT = dp2px(44f)
    private var activity: Activity? = null

    constructor(context: Context): super(context)

    constructor(context: Context, @Nullable attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, @Nullable attrs: AttributeSet, @AttrRes defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        setBackgroundColor(Color.WHITE)
        minimumHeight = DEF_HEIGHT
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.gravity = Gravity.CENTER_VERTICAL
        params.leftMargin = dp2px(12f)
        addView(leftTv, params)

        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        lp.gravity = Gravity.CENTER
        addView(centerTv, lp)

        val params2 = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params2.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        params2.rightMargin = dp2px(12f)
        addView(rightTv, params2)
        addView(rightIv, params2)

        leftTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_left_black_34dp, 0,0,0)

        centerTv.gravity = Gravity.CENTER
        leftTv.gravity = Gravity.CENTER_VERTICAL
        rightTv.gravity = Gravity.CENTER_VERTICAL

        leftTv.setTextColor(ContextCompat.getColor(context, R.color.color_333))
        centerTv.setTextColor(ContextCompat.getColor(context, R.color.color_333))
        rightTv.setTextColor(ContextCompat.getColor(context, R.color.color_333))

        centerTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        rightTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
        leftTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)

        leftTv.setOnClickListener {
            if(activity != null)
                activity?.finish()
        }
    }

    fun hideLeft(): TitleBar{
        leftTv.visibility = View.GONE
        return this
    }

    fun init(activity: Activity): TitleBar{
        this.activity = activity
        return this
    }

    fun height(height: Int): TitleBar{
        minimumHeight = height
        return this
    }

    fun textRight(right: String): TitleBar{
        rightTv.text = right
        rightTv.visibility = View.VISIBLE
        rightIv.visibility = View.GONE
        return this
    }

    fun textRight(@StringRes resId: Int): TitleBar{
        rightTv.setText(resId)
        rightTv.visibility = View.VISIBLE
        rightIv.visibility = View.GONE
        return this
    }

    fun textLeft(left: String): TitleBar{
        leftTv.text = left
        return this
    }

    fun textTitle(title: String): TitleBar{
        centerTv.text = title
        return this
    }

    fun textTitle(@StringRes resId: Int): TitleBar{
        centerTv.setText(resId)
        return this
    }

    fun leftClick(listener: OnClickListener): TitleBar{
        leftTv.setOnClickListener(listener)
        return this
    }

    fun rightClick(listener: OnClickListener): TitleBar{
        rightTv.setOnClickListener(listener)
        return this
    }

    fun colorRight(@ColorInt color: Int): TitleBar{
        rightTv.setTextColor(color)
        return this
    }

    fun drawableLeft(@DrawableRes left: Int): TitleBar{
        leftTv.setCompoundDrawablesWithIntrinsicBounds(left, 0,0,0)
        return this
    }

    fun drawableRight(@DrawableRes right: Int): TitleBar{
        rightIv.setImageResource(right)
        rightTv.visibility = View.GONE
        rightIv.visibility = View.VISIBLE
        return this
    }

    fun dp2px(dp: Float): Int{
        return (context.resources.displayMetrics.density * dp + 0.5f).toInt()
    }

}