package com.alien.base.http

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ReeseLuo on 2019/1/5.
 */
data class HttpResult<T> constructor(@Expose
                    @SerializedName("result")
                    val result: Int,

                    @Expose
                    @SerializedName("state")
                    val state: Boolean,

                    @Expose
                    @SerializedName("msg")
                    val msg: String,

                    @Expose
                    @SerializedName("data")
                    val data: T) {
}