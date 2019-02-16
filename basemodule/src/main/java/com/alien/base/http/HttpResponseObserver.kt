package com.alien.base.http

import com.alien.base.event.RxBus
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class HttpResponseObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        if(t is HttpResult<*>){
            val result = t as HttpResult<*>
            if(result.state){
                if (result.data != null)
                    onResult(t)
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
                onResult(t)
            }else{
                val httpThrowable = HttpThrowable(response.result, response.msg)
                onError(ApiError(httpThrowable))
            }
        }else{
            onResult(t)
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