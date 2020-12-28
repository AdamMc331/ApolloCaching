package com.adammcneilly.apollocaching.data

import com.adammcneilly.apollocaching.CountryDetailQuery
import com.adammcneilly.apollocaching.CountryListQuery
import com.adammcneilly.apollocaching.models.CountryDetail
import com.adammcneilly.apollocaching.models.CountryOverview
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.coroutines.await

class ApolloCountryService(
    private val apolloClient: ApolloClient
) : CountryRepository {

    override suspend fun getCountryList(): List<CountryOverview> {
        val query = CountryListQuery()
        val response = apolloClient.query(query).await()
        return response.data?.toCountryOverviewList().orEmpty()
    }

    override suspend fun getCountryDetail(countryCode: String): CountryDetail? {
        val query = CountryDetailQuery(code = countryCode)
        val response = apolloClient.query(query)
            .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
            .await()
        return response.data?.toCountryDetail()

        // apolloClient.query(query)
        //    .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
        //    .toDeferred()
        //    .await()
    }
}

private fun CountryListQuery.Data.toCountryOverviewList(): List<CountryOverview> {
    return this.countries.map { country ->
        CountryOverview(
            code = country.code,
            name = country.name
        )
    }
}

private fun CountryDetailQuery.Data.toCountryDetail(): CountryDetail {
    val country = this.country

    return CountryDetail(
        code = country?.code.orEmpty(),
        name = country?.name.orEmpty(),
        continentName = country?.continent?.name.orEmpty(),
        capital = country?.capital.orEmpty(),
        emoji = country?.emoji.orEmpty()
    )
}
