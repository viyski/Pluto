package com.alien.base.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HttpHeaderInterceptor(val headers: Map<String, String>): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalBuilder = originalRequest.newBuilder()
        for (entry in headers.entries) {
            originalBuilder.header(entry.key, entry.value)
        }

        val newBuilder = originalBuilder.method(originalRequest.method(), originalRequest.body())
        return chain.proceed(newBuilder.build())
    }


}