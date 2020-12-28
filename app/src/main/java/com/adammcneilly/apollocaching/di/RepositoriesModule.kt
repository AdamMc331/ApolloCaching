package com.adammcneilly.apollocaching.di

import com.adammcneilly.apollocaching.data.ApolloCountryService
import com.adammcneilly.apollocaching.data.CountryRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory<CountryRepository> {
        ApolloCountryService(
            apolloClient = get()
        )
    }
}
