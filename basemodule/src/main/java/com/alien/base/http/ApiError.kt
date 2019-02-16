package com.alien.base.http

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

class ApiError(e: Throwable?): Exception() {

    companion object {
        const val ERROR_TYPE_UNKNOWN = -1
        const val ERROR_TYPE_CONNECT = -2 // 连接超时
        const val ERROR_TYPE_HTTP = -3 // 连接超时

        const val ERROR_BAD_QEQUEST = 400
        const val ERROR_UNAUTHORIZED = 401
        const val ERROR_FORBIDDEN = 403
    }

    var error: Error? = null
    var msg: String = ""
    var code: Int = 0
    var errorType: Int = 0

    init {
        if (e is HttpThrowable) {
            this.errorType = ERROR_TYPE_HTTP
            this.code = e.code
            this.msg = e.message!!
        }else if (e is HttpException) {
            this.errorType = ERROR_TYPE_HTTP
            this.code = e.code()
            this.msg = e.message()
            try {
                val error = e.response().errorBody()!!.string()
                Logger.e("value %s", error)
                parseErrorBody(error)
            } catch (e1: IOException) {
                Logger.e("get message %s", e1)
            } catch (e2: JsonSyntaxException) {
                e2.printStackTrace()
            } catch (e3: Exception) {
                e3.printStackTrace()
            }

        } else if (e is RuntimeException) {
            val exception = e
            this.msg = e.message!!
        } else if (e is ConnectException) {
            this.errorType = ERROR_TYPE_CONNECT
            this.msg = e.message!!
        }else {
            this.errorType = ERROR_TYPE_UNKNOWN
            this.msg = e?.message!!
            Logger.e("error %s", this)
        }
    }

    private fun parseErrorBody(errorBody: String) {
        Logger.e("error %s", errorBody)
        if (TextUtils.isEmpty(errorBody)) {
            return
        }
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    inner class Error {
        var code: Int = 0
        var message: String? = null
        var extra: String? = null
    }
}