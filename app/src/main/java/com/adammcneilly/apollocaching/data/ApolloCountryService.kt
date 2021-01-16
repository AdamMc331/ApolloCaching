package com.adammcneilly.apollocaching.data

import com.adammcneilly.apollocaching.CountryDetailQuery
import com.adammcneilly.apollocaching.CountryListQuery
import com.adammcneilly.apollocaching.fragment.CountryDetailFragment
import com.adammcneilly.apollocaching.models.CountryDetail
import com.adammcneilly.apollocaching.models.CountryOverview
import com.apollographql.apollo.ApolloClient
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

        val response = apolloClient.query(query).await()

        return response.data?.country?.fragments?.countryDetailFragment?.toCountryDetail()
    }
}

private fun CountryListQuery.Data.toCountryOverviewList(): List<CountryOverview> {
    return this.countries.map { country ->
        country.fragments.countryDetailFragment.toCountryOverview()
    }
}

private fun CountryDetailFragment.toCountryOverview(): CountryOverview {
    return CountryOverview(
        code = this.code,
        name = this.name
    )
}

private fun CountryDetailFragment.toCountryDetail(): CountryDetail {
    return CountryDetail(
        code = this.code,
        name = this.name,
        continentName = this.continent.name,
        capital = this.capital.orEmpty(),
        emoji = this.emoji,
    )
}
