package com.alien.base.http

import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.reflect.ParameterizedType

abstract class JsonResponseObserver<T> : Observer<String> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(str: String) {
        try {
            val type = javaClass.genericSuperclass as ParameterizedType
            val clazz = type.actualTypeArguments[0] as Class<T>
            val t = GsonBuilder().create().fromJson(str, clazz)

            if(t is HttpResult<*>){
                val result = t as HttpResult<*>
                if(result.state){
                    if (result.data != null)
                        onResult(t as T)
                    else{
                        val httpThrowable = HttpThrowable(result.result, result.msg)
                        onError(ApiError(httpThrowable))
                    }
                }else{
                    val httpThrowable = HttpThrowable(result.result, result.msg)
                    onError(ApiError(httpThrowable))
                }
            }else if (t is HttpResponse){
                val response = t as HttpResponse
                if(response.state){
                    onResult(t as T)
                }else{
                    val httpThrowable = HttpThrowable(response.result, response.msg)
                    onError(ApiError(httpThrowable))
                }
            }else{
                onResult(t as T)
            }
        } catch (e: Exception) {
            onError(ApiError(e))
        }
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        Logger.e(e, "error message : %s", e.message)
        val error = ApiError(e)
        onError(error)
    }

    abstract fun onResult(result: T)

    abstract fun onError(error: ApiError)
}