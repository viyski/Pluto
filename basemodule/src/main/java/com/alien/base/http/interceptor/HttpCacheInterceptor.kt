package com.alien.base.http.interceptor

import android.content.Context
import com.alien.base.tools.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by ReeseLuo on 2019/1/6.
 */
class HttpCacheInterceptor(val context: Context, private val mCacheTimeWithNet: Int = 0, private val mCacheTimeWithoutNet: Int = 0): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetWorkUtils.isNetAvailable(context)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }

        val response = chain.proceed(request)
        if (NetWorkUtils.isNetAvailable(context)) {
            val maxAge = if (mCacheTimeWithNet > 0) mCacheTimeWithNet else 60
            val cacheControl = "public,max-age=$maxAge"
            return response.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build()
        } else {
            val maxStale = if (mCacheTimeWithoutNet > 0) mCacheTimeWithoutNet else 60 * 60 * 24 * 7 * 1
            return response.newBuilder().header("Cache-Control", "public,only-if-cached,max-stale=$maxStale").removeHeader("Pragma").build()
        }
    }

}