package com.adammcneilly.apollocaching.data

import com.adammcneilly.apollocaching.models.CountryDetail
import com.adammcneilly.apollocaching.models.CountryOverview

interface CountryRepository {
    suspend fun getCountryList(): List<CountryOverview>

    suspend fun getCountryDetail(countryCode: String): CountryDetail?
}
