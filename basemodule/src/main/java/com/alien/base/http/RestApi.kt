package com.alien.base.http

import android.annotation.SuppressLint
import android.content.Context
import android.os.Process
import com.alibaba.fastjson.JSON
import com.alien.base.BuildConfig
import com.alien.base.http.interceptor.HttpCacheInterceptor
import com.alien.base.http.interceptor.HttpHeaderInterceptor
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestApi @Inject constructor(val context: Context, val httpConfig: HttpConfig) {

    companion object {

        const val CONNECT_TIME_OUT = 30L
        const val READ_TIME_OUT = 30L
        const val WRITE_TIME_OUT = 30L
        const val MAX_POOL_SIZE = 10
        const val INITIAL_CORE_POOL_SIZE = 1
        const val KEEP_ALIVE_TIME = 60L

        val DEBUG = BuildConfig.DEBUG
        val CORE_POOL_SIZE = Math.min(MAX_POOL_SIZE, Runtime.getRuntime().availableProcessors() + 1)

        val HTTP_EXECUTOR = ThreadPoolExecutor(INITIAL_CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, LinkedBlockingQueue()
        ) { r ->
            Thread({
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
                r.run()
            }, "DaJiaHttp-Idle")
        }
    }

    private var mOkHttpClient: OkHttpClient? = null

    fun retrofitNet(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(setupOkHttpClient())
            .build()
    }

    fun setupOkHttpClient(): OkHttpClient {
        if (mOkHttpClient == null) {
            val loggingInterceptor = getHttpLoggingInterceptor()
            val cacheInterceptor = HttpCacheInterceptor(context)

            mOkHttpClient = OkHttpClient.Builder()
                .dispatcher(Dispatcher(HTTP_EXECUTOR))
                .cache(getHttpCache())
                .addInterceptor(HttpHeaderInterceptor(httpConfig.headerMap))
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build()
        }

        return mOkHttpClient!!
    }

    private fun getHttpCache(): Cache {
        val httpCacheDirectory = File(context.cacheDir, "responses")
        return Cache(httpCacheDirectory, (10 * 1024 * 1024).toLong())
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        val logLevel = if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        loggingInterceptor.level = logLevel
        return loggingInterceptor
    }

    @SuppressLint("CheckResult")
    fun <T> okhttpCall(url: String, requestBody: Any, clazz: Class<T>, observer: JsonResponseObserver<T>){
        val okHttpClient = setupOkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(requestBody)))
            .build()
        Observable.create<String> {
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
                        it.onNext(response.body()?.string()!!)
                    }else{
                        it.onError(RuntimeException("http exception"))
                    }
                }
            })
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(observer)
    }

}