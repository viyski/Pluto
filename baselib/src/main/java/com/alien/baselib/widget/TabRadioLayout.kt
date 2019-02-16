package com.alien.baselib.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alien.baselib.R

class TabRadioLayout : RadioGroup {

    private var radioType: Int = 0
    private var drawablePadding: Float = 0f
    private var color: Int = 0x000000
    private var textSize: Int = 12
    private var sparseArray: SparseArray<Int> = SparseArray()

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet): super(context, attr){
        val ty = context.obtainStyledAttributes(attr, R.styleable.TabRadioLayout)
        radioType = ty.getInt(R.styleable.TabRadioLayout_tab_type, 0)
        drawablePadding = ty.getDimension(R.styleable.TabRadioLayout_tab_drawable_padding, 0f)
        color = ty.getResourceId(R.styleable.TabRadioLayout_tab_text_color, android.R.color.black)
        textSize = ty.getDimensionPixelOffset(R.styleable.TabRadioLayout_tab_text_size, 12)
        ty.recycle()
    }

    init {
        orientation = RadioGroup.HORIZONTAL
    }

    fun addTab(tab: Tab){
        sparseArray.put(tab.id, childCount)
        addView(tab)
    }

    fun newTab(): Tab {
        val params = RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        params.weight = 1f

        val button = Tab(context)
        button.setButtonDrawable(0)
        button.drawablePadding = drawablePadding
        button.type = radioType
        button.gravity = Gravity.CENTER
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        button.setTextColor(resources.getColorStateList(color))
        button.layoutParams = params
        button.id = R.id.tab_id + childCount
        return button
    }

    fun addOnTabSelectedListener(listener: ViewPagerOnTabSelectedListener){
        setOnCheckedChangeListener(listener)
        (getChildAt(0) as RadioButton).isChecked = true
    }

    fun onPageSelected(position: Int){
        val index = sparseArray.indexOfValue(position)
        val checkId = sparseArray.keyAt(index)
        if(findViewById<RadioButton>(checkId) != null){
            findViewById<RadioButton>(checkId).isChecked = true
        }
    }

    class ViewPagerOnTabSelectedListener(val viewPager: ViewPager): RadioGroup.OnCheckedChangeListener{

        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            val tabLayout = group as TabRadioLayout
            viewPager.currentItem = tabLayout.sparseArray.get(checkedId, 0)
        }

    }

    class Tab : RadioButton{

        internal var type: Int = 0
        internal var drawablePadding: Float = 0f

        constructor(context: Context): super(context)

        constructor(context: Context, attr: AttributeSet): super(context, attr)

        fun setIcon(resId: Int): Tab{
            compoundDrawablePadding = drawablePadding.toInt()
            if (type == 0) {
                setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0)
            }else{
                setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0)
            }
            return this
        }

        fun setTabText(text: String): Tab{
            setText(text)
            return this
        }

        fun setTabText(res: Int): Tab{
            setText(res)
            return this
        }
    }
}