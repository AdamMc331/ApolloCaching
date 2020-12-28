package com.adammcneilly.apollocaching.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCache
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import okhttp3.Interceptor
import okhttp3.internal.cache.CacheInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

private const val BYTES_PER_KILOBYTE = 1024L
private const val KILOBYTES_PER_MEGABYTE = 1024L

val networkingModule = module {

    factory<HttpCache> {
        val file = File(androidContext().cacheDir, "apolloCache")
        val numMegabytes = 1
        val sizeInMegabytes = BYTES_PER_KILOBYTE * KILOBYTES_PER_MEGABYTE * numMegabytes

        ApolloHttpCache(DiskLruHttpCacheStore(file, sizeInMegabytes))
    }

    factory {
        ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/")
            .httpCache(get())
            .build()
    }
}
