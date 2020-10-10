package com.dajie.luckyweather.logic.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Query
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**

 *  Created by DaJie on 2020/10/10
 *  统一的网络数据源访问入口

 */
object LuckyWeatherNetwork {
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    suspend fun searchPlaces(query: String) = placeService.seachPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(
                object :Callback<T>{
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        it.resumeWithException(t)
                    }

                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        val body = response.body()
                        if(null!=body) it.resume(body)
                        else it.resumeWithException(RuntimeException("response body is null"))
                    }

                }
            )
        }
    }
}