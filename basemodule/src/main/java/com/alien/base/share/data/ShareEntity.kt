package com.alien.base.share.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShareEntity constructor(
        @Expose
        @SerializedName("title")
        val title: String,

        @Expose
        @SerializedName("icon")
        val icon: String,

        @Expose
        @SerializedName("id")
        val id: Long,

        @Expose
        @SerializedName("text")
        val text: String,

        @Expose
        @SerializedName("url")
        val url: String,

        @Expose
        @SerializedName("type")
        val type: Int,

        @Expose
        @SerializedName("redirect")
        val redirect: String,

        val platformType: String,

        val isLocalUrl: Boolean

) : Serializable{
}