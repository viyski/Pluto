package com.alien.base.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.TypedValue
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.alien.base.R


class ActionBottomSheet: Fragment(), View.OnClickListener {

    companion object {
        private val ARG_CANCEL_VIEW_TITLE = "cancel_view_title"
        private val ARG_OTHER_VIEW_TITLES = "other_view_titles"
        private val ARG_CANCELABLE_ONTOUCHOUTSIDE = "cancelable_ontouchoutside"
    }

    private val CANCEL_VIEW_ID = 100
    private val BG_VIEW_ID = 10
    private val TRANSLATE_DURATION = 200
    private val ALPHA_DURATION = 300

    private var mDecorView: ViewGroup? = null
    private var mRootView: View? = null
    private var mBg: View? = null
    private var mPanel: LinearLayout? = null
    private var mAttrs: Attributes? = null
    private var navigationBarHeight = 0 // 导航栏高度

    private var isCancel = true
    private var mDismissed = true
    private var hasTitle = false
    private var mListener: ActionSheetListener? = null

    fun show(manager: FragmentManager, tag: String) {
        if (!mDismissed) {
            return
        }
        mDismissed = false
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun dismiss() {
        if (mDismissed) {
            return
        }
        mDismissed = true
        fragmentManager!!.popBackStack()
        val ft = fragmentManager!!.beginTransaction()
        ft.remove(this)
        ft.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive()) {
            val focusView = activity!!.currentFocus
            if (focusView != null) {
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
            }
        }

        mDecorView = activity!!.window.decorView as ViewGroup
        mRootView = createView()
        mAttrs = readAttribute()
        createItems()
        mDecorView!!.addView(mRootView)

        mBg!!.startAnimation(createAlphaInAnimation())
        mPanel!!.startAnimation(createTranslationInAnimation())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun createView(): View {
        mBg = View(activity)
        mBg!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mBg!!.setBackgroundColor(Color.argb(136, 0, 0, 0))
        mBg!!.id = BG_VIEW_ID
        mBg!!.setOnClickListener(this)

        mPanel = LinearLayout(activity)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.BOTTOM

        mPanel!!.layoutParams = params
        mPanel!!.orientation = LinearLayout.VERTICAL

        if (hasNavigationBar(activity!!)) {
            navigationBarHeight = getNavigationBarHeight(activity!!)
        }

        val parent = FrameLayout(activity!!)
        val parentParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        parentParams.bottomMargin = navigationBarHeight
        parent.layoutParams = parentParams
        parent.addView(mBg)
        parent.addView(mPanel)
        return parent
    }

    private fun createItems() {
        val titles = getOtherTextViewTitles()
        if (titles != null) {
            for (i in titles.indices) {
                val bt = TextView(activity)
                bt.id = CANCEL_VIEW_ID + i + 1
                if (hasTitle && i == 0) {
                    bt.setTextColor(Color.parseColor("#9e9e9e"))
                    bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs!!.actionSheetTextSize * 0.8f)
                    bt.setPadding(40, 40, 40, 40)
                } else {
                    bt.setOnClickListener(this)
                    bt.setTextColor(mAttrs!!.otherTextViewTextColor)
                    bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs!!.actionSheetTextSize)
                }
                bt.setBackgroundDrawable(getOtherTextViewBg(titles, i))
                bt.text = titles[i]
                bt.gravity = Gravity.CENTER
                if (i > 0) {
                    val params = createTextViewLayoutParams()
                    params.topMargin = mAttrs!!.otherTextViewSpacing
                    mPanel!!.addView(bt, params)
                } else {
                    mPanel!!.addView(bt)
                }
            }
        }
        val bt = TextView(activity)
        bt.paint.isFakeBoldText = true
        bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs!!.actionSheetTextSize)
        bt.id = CANCEL_VIEW_ID
        bt.setBackgroundDrawable(mAttrs!!.cancelTextViewBackground)
        bt.text = getCancelTextViewTitle()
        bt.gravity = Gravity.CENTER
        bt.setTextColor(mAttrs!!.cancelTextViewTextColor)
        bt.setOnClickListener(this)
        val params = createTextViewLayoutParams()
        params.topMargin = mAttrs!!.cancelTextViewMarginTop
        mPanel!!.addView(bt, params)

        mPanel!!.setBackgroundDrawable(mAttrs!!.background)
        mPanel!!.setPadding(mAttrs!!.padding, mAttrs!!.padding, mAttrs!!.padding, mAttrs!!.padding)
    }

    private fun createTranslationInAnimation(): Animation {
        val type = TranslateAnimation.RELATIVE_TO_SELF
        val an = TranslateAnimation(type, 0f, type, 0f, type, 1f, type, 0f)
        an.duration = TRANSLATE_DURATION.toLong()
        return an
    }

    private fun createAlphaInAnimation(): Animation {
        val an = AlphaAnimation(0f, 1f)
        an.duration = ALPHA_DURATION.toLong()
        return an
    }

    private fun createTranslationOutAnimation(): Animation {
        val type = TranslateAnimation.RELATIVE_TO_SELF
        val an = TranslateAnimation(type, 0f, type, 0f, type, 0f, type, 1f)
        an.duration = TRANSLATE_DURATION.toLong()
        an.fillAfter = true
        return an
    }

    private fun createAlphaOutAnimation(): Animation {
        val an = AlphaAnimation(1f, 0f)
        an.duration = ALPHA_DURATION.toLong()
        an.fillAfter = true
        return an
    }

    fun createTextViewLayoutParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun getOtherTextViewBg(titles: Array<CharSequence>, i: Int): Drawable? {
        if (titles.size == 1) {
            return mAttrs!!.otherTextViewSingleBackground
        }
        if (titles.size == 2) {
            when (i) {
                0 -> return mAttrs!!.otherTextViewTopBackground
                1 -> return mAttrs!!.otherTextViewBottomBackground
            }
        }
        if (titles.size > 2) {
            if (i == 0) {
                return mAttrs!!.otherTextViewTopBackground
            }
            return if (i == titles.size - 1) {
                mAttrs!!.otherTextViewBottomBackground
            } else mAttrs!!.getOtherTextViewMiddleBackground()
        }
        return null
    }

    override fun onDestroyView() {
        mPanel!!.startAnimation(createTranslationOutAnimation())
        mBg!!.startAnimation(createAlphaOutAnimation())
        mRootView!!.postDelayed({ mDecorView!!.removeView(mRootView) }, ALPHA_DURATION.toLong())
        if (mListener != null) {
            mListener!!.onDismiss(this, isCancel)
        }
        super.onDestroyView()
    }

    private fun readAttribute(): Attributes {
        val attrs = Attributes(activity!!)
        val a = activity!!.theme.obtainStyledAttributes(null,
                R.styleable.ActionSheet, R.attr.actionSheetStyle, 0)
        val background = a.getDrawable(R.styleable.ActionSheet_actionSheetBackground)
        if (background != null) {
            attrs.background = background
        }
        val cancelTextViewBackground = a.getDrawable(R.styleable.ActionSheet_cancelTextViewBackground)
        if (cancelTextViewBackground != null) {
            attrs.cancelTextViewBackground = cancelTextViewBackground
        }
        val otherTextViewTopBackground = a.getDrawable(R.styleable.ActionSheet_otherTextViewTopBackground)
        if (otherTextViewTopBackground != null) {
            attrs.otherTextViewTopBackground = otherTextViewTopBackground
        }
        val otherTextViewMiddleBackground = a.getDrawable(R.styleable.ActionSheet_otherTextViewMiddleBackground)
        if (otherTextViewMiddleBackground != null) {
            attrs.otherTextViewMiddleBackground = otherTextViewMiddleBackground
        }
        val otherTextViewBottomBackground = a.getDrawable(R.styleable.ActionSheet_otherTextViewBottomBackground)
        if (otherTextViewBottomBackground != null) {
            attrs.otherTextViewBottomBackground = otherTextViewBottomBackground
        }
        val otherTextViewSingleBackground = a.getDrawable(R.styleable.ActionSheet_otherTextViewSingleBackground)
        if (otherTextViewSingleBackground != null) {
            attrs.otherTextViewSingleBackground = otherTextViewSingleBackground
        }
        attrs.cancelTextViewTextColor = a.getColor(
                R.styleable.ActionSheet_cancelTextViewTextColor,
                attrs.cancelTextViewTextColor)
        attrs.otherTextViewTextColor = a.getColor(
                R.styleable.ActionSheet_otherTextViewTextColor,
                attrs.otherTextViewTextColor)
        attrs.padding = a.getDimension(
                R.styleable.ActionSheet_actionSheetPadding, attrs.padding.toFloat()).toInt()
        attrs.otherTextViewSpacing = a.getDimension(
                R.styleable.ActionSheet_otherTextViewSpacing,
                attrs.otherTextViewSpacing.toFloat()).toInt()
        attrs.cancelTextViewMarginTop = a.getDimension(
                R.styleable.ActionSheet_cancelTextViewMarginTop,
                attrs.cancelTextViewMarginTop.toFloat()).toInt()
        attrs.actionSheetTextSize = a.getDimensionPixelSize(R.styleable.ActionSheet_actionSheetTextSize, attrs.actionSheetTextSize.toInt()).toFloat()

        a.recycle()
        return attrs
    }

    private fun getCancelTextViewTitle(): CharSequence? {
        return arguments!!.getCharSequence(ARG_CANCEL_VIEW_TITLE)
    }

    private fun getOtherTextViewTitles(): Array<CharSequence>? {
        return arguments!!.getCharSequenceArray(ARG_OTHER_VIEW_TITLES)
    }

    private fun getCancelableOnTouchOutside(): Boolean {
        return arguments!!.getBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE)
    }

    fun setActionSheetListener(listener: ActionSheetListener) {
        mListener = listener
    }

    override fun onClick(v: View) {
        if (v.id == BG_VIEW_ID && !getCancelableOnTouchOutside()) {
            return
        }
        dismiss()
        if (v.id != CANCEL_VIEW_ID && v.id != BG_VIEW_ID) {
            if (mListener != null) {
                mListener!!.onOtherTextViewClick(this, v.id - CANCEL_VIEW_ID
                        - 1)
            }
            isCancel = false
        }
    }

    fun createBuilder(context: Context, fragmentManager: FragmentManager): Builder {
        hasTitle = false
        return Builder(context, fragmentManager)
    }

    fun createBuilder(context: Context, hasTitleItem: Boolean, fragmentManager: FragmentManager): Builder {
        hasTitle = hasTitleItem
        return Builder(context, fragmentManager)
    }


    fun hasNavigationBar(context: Context): Boolean {
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !hasMenuKey && !hasBackKey
    }

    fun getNavigationBarHeight(activity: Activity): Int {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    private class Attributes(private val mContext: Context) {

        internal var background: Drawable
        internal var cancelTextViewBackground: Drawable
        internal var otherTextViewTopBackground: Drawable
        internal var otherTextViewMiddleBackground: Drawable? = null
        internal var otherTextViewBottomBackground: Drawable
        internal var otherTextViewSingleBackground: Drawable
        internal var cancelTextViewTextColor: Int = 0
        internal var otherTextViewTextColor: Int = 0
        internal var padding: Int = 0
        internal var otherTextViewSpacing: Int = 0
        internal var cancelTextViewMarginTop: Int = 0
        internal var actionSheetTextSize: Float = 0.toFloat()

        init {
            this.background = ColorDrawable(Color.TRANSPARENT)
            this.cancelTextViewBackground = ColorDrawable(Color.BLACK)
            val gray = ColorDrawable(Color.GRAY)
            this.otherTextViewTopBackground = gray
            this.otherTextViewMiddleBackground = gray
            this.otherTextViewBottomBackground = gray
            this.otherTextViewSingleBackground = gray
            this.cancelTextViewTextColor = Color.WHITE
            this.otherTextViewTextColor = Color.BLACK
            this.padding = dp2px(20)
            this.otherTextViewSpacing = dp2px(2)
            this.cancelTextViewMarginTop = dp2px(10)
            this.actionSheetTextSize = dp2px(14).toFloat()
        }

        private fun dp2px(dp: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dp.toFloat(), mContext.getResources().getDisplayMetrics()).toInt()
        }

        fun getOtherTextViewMiddleBackground(): Drawable? {
            if (otherTextViewMiddleBackground is StateListDrawable) {
                val a = mContext.getTheme().obtainStyledAttributes(null,
                        R.styleable.ActionSheet, R.attr.actionSheetStyle, 0)
                otherTextViewMiddleBackground = a
                        .getDrawable(R.styleable.ActionSheet_otherTextViewMiddleBackground)
                a.recycle()
            }
            return otherTextViewMiddleBackground
        }
    }

    class Builder(private val mContext: Context, private val mFragmentManager: FragmentManager) {
        private var mCancelTextViewTitle: String? = null
        private var mOtherTextViewTitles: Array<String>? = null
        private var mTag = "actionSheet"
        private var mCancelableOnTouchOutside: Boolean = false
        private var mListener: ActionSheetListener? = null

        fun setCancelTextViewTitle(title: String): Builder {
            mCancelTextViewTitle = title
            return this
        }

        fun setCancelTextViewTitle(strId: Int): Builder {
            return setCancelTextViewTitle(mContext.getString(strId))
        }

        fun setOtherTextViewTitles(vararg titles: String): Builder {
            mOtherTextViewTitles = titles.asList().toTypedArray()
            return this
        }

        fun setTag(tag: String): Builder {
            mTag = tag
            return this
        }

        fun setListener(listener: ActionSheetListener): Builder {
            this.mListener = listener
            return this
        }

        fun setCancelableOnTouchOutside(cancelable: Boolean): Builder {
            mCancelableOnTouchOutside = cancelable
            return this
        }

        fun prepareArguments(): Bundle {
            val bundle = Bundle()
            bundle.putCharSequence(ARG_CANCEL_VIEW_TITLE, mCancelTextViewTitle)
            bundle.putCharSequenceArray(ARG_OTHER_VIEW_TITLES, mOtherTextViewTitles)
            bundle.putBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE, mCancelableOnTouchOutside)
            return bundle
        }

        fun show(): ActionBottomSheet {
            val actionSheet = Fragment.instantiate(mContext, ActionBottomSheet::class.java.name, prepareArguments()) as ActionBottomSheet
            actionSheet.setActionSheetListener(mListener!!)
            actionSheet.show(mFragmentManager, mTag)
            return actionSheet
        }
    }

    interface ActionSheetListener {
        fun onDismiss(actionSheet: ActionBottomSheet, isCancel: Boolean)
        fun onOtherTextViewClick(actionSheet: ActionBottomSheet, index: Int)
    }
}