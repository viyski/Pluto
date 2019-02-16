package com.alien.base.image

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.alien.base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

object GlideLoader {

    fun load(url: String?, imageView: ImageView){
        Glide.with(imageView.context)
                .load(url)
                .into(imageView)
    }

    fun loadBlur(url: String?, imageView: ImageView){
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions().transform(BlurTransformation(25, 6)))
                .into(imageView)
    }

    fun loadBlur(url: String?, imageView: ImageView, sampling: Int = 3, placeholder: Int = android.R.color.transparent){
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions()
                .transform(BlurTransformation(25, sampling))
                .placeholder(placeholder))
                .into(imageView)
    }

    fun loadRes(resId: Int, imageView: ImageView) {
        Glide.with(imageView.context)
                .load(resId)
                .apply(RequestOptions()
                .placeholder(resId))
                .into(imageView)
    }

    fun loadCircle(url: String?, imageView: ImageView, @DrawableRes resId: Int = R.drawable.ic_avatar){
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions()
                .circleCrop()
                .placeholder(resId))
                .into(imageView)
    }

}