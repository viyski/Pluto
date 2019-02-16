package com.alien.base.http

import io.reactivex.Observable
import retrofit2.http.*

interface HttpApiService {

    @GET
    fun getFromHttpServer(@Url interfaceUrl: String, @QueryMap options: Map<String, Any>): Observable<Any>

    @POST
    fun postFromHttpServer(@Url interfaceUrl: String, @Body options: Map<String, Any>): Observable<Any>

    @PUT
    fun putFromHttpServer(@Url interfaceUrl: String, @Body options: Map<String, Any>): Observable<Any>

    @DELETE
    fun deleteFromHttpServer(@Url interfaceUrl: String): Observable<Any>

    @GET
    fun getFromHttpServerVoid(@Url interfaceUrl: String, @QueryMap options: Map<String, Any>): Observable<Void>

    @POST
    fun postFromHttpServerVoid(@Url interfaceUrl: String, @Body options: Map<String, Any>): Observable<Void>

    @PUT
    fun putFromHttpServerVoid(@Url interfaceUrl: String, @Body options: Map<String, Any>): Observable<Void>

    @DELETE
    fun deleteFromHttpServerVoid(@Url interfaceUrl: String): Observable<Void>
}