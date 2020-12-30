package com.adammcneilly.apollocaching.di

import com.adammcneilly.apollocaching.data.ApolloAndroidLogger
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.Logger
import com.apollographql.apollo.api.cache.http.HttpCache
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

private const val BYTES_PER_KILOBYTE = 1024L
private const val KILOBYTES_PER_MEGABYTE = 1024L

val networkingModule = module {

    single<Logger> {
        ApolloAndroidLogger()
    }

    /**
     * An [HttpCache] is a limited but easier to use caching layer for the Apollo library.
     *
     * We create the [File] that our cache is stored in, define the size of our cache in bytes,
     * and return this instance.
     *
     * Full docs: https://www.apollographql.com/docs/android/essentials/http-cache/
     */
    single<HttpCache> {
        val file = File(androidContext().cacheDir, "apolloCache")
        val numMegabytes = 1
        val sizeInMegabytes = BYTES_PER_KILOBYTE * KILOBYTES_PER_MEGABYTE * numMegabytes

        val cacheStore = DiskLruHttpCacheStore(file, sizeInMegabytes)

        ApolloHttpCache(cacheStore, get())
    }

    single<NormalizedCacheFactory<*>> {
        val numMegabytes = 10
        val sizeInMegabytes = BYTES_PER_KILOBYTE * KILOBYTES_PER_MEGABYTE * numMegabytes

        val evictionPolicy = EvictionPolicy.builder()
            .maxSizeBytes(sizeInMegabytes)
            .build()

        LruNormalizedCacheFactory(evictionPolicy)
    }

    single {
        ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/")
            .httpCache(get())
            .normalizedCache(get())
            .logger(get())
            .build()
    }
}
