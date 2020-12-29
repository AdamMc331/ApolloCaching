package com.adammcneilly.apollocaching.di

import com.adammcneilly.apollocaching.data.ApolloAndroidLogger
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCache
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

private const val BYTES_PER_KILOBYTE = 1024L
private const val KILOBYTES_PER_MEGABYTE = 1024L

val networkingModule = module {

    /**
     * An [HttpCache] is a limited but easier to use caching layer for the Apollo library.
     *
     * We create the [File] that our cache is stored in, define the size of our cache in bytes,
     * and return this instance.
     *
     * Full docs: https://www.apollographql.com/docs/android/essentials/http-cache/
     */
    factory<HttpCache> {
        val file = File(androidContext().cacheDir, "apolloCache")
        val numMegabytes = 1
        val sizeInMegabytes = BYTES_PER_KILOBYTE * KILOBYTES_PER_MEGABYTE * numMegabytes

        val cacheStore = DiskLruHttpCacheStore(file, sizeInMegabytes)
        val logger = ApolloAndroidLogger()

        ApolloHttpCache(cacheStore, logger)
    }

    factory {
        ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/")
            .httpCache(get())
            .build()
    }
}
