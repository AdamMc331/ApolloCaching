package com.adammcneilly.apollocaching.di

import com.apollographql.apollo.ApolloClient
import org.koin.dsl.module

val networkingModule = module {
    factory {
        ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/")
            .build()
    }
}
