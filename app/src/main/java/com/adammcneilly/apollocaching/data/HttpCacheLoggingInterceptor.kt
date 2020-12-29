package com.adammcneilly.apollocaching.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HttpCacheLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val operationName = request.header("X-APOLLO-OPERATION-NAME")

        val response = chain.proceed(request)

        val wasFromCache = (response.cacheResponse() != null)
        Log.d(
            HttpCacheLoggingInterceptor::class.java.simpleName,
            "Response For: $operationName From Cache: $wasFromCache"
        )

        return response
    }
}
