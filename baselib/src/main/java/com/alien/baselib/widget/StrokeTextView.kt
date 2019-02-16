package com.alien.baselib.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.alien.baselib.R

open class StrokeTextView : AppCompatTextView {

    private var strokeWidth: Float = 6f
    private var strokeColor = Color.WHITE
    private var textColorInt = Color.WHITE

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView)
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.StrokeTextView_stroke_width, 3).toFloat()
        strokeColor = typedArray.getColor(R.styleable.StrokeTextView_stroke_color, Color.WHITE)
        typedArray.recycle()
    }

    fun setStrokeColor(strokeColor: Int, textColor: Int){
        this.strokeColor = strokeColor
        this.textColorInt = textColor
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeMiter = 10f
        paint.strokeWidth = strokeWidth

        setTextColor(strokeColor)

        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        setTextColor(textColorInt)
        super.onDraw(canvas)
    }
}