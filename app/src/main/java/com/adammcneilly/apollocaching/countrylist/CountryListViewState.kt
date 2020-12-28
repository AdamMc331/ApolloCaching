package com.adammcneilly.apollocaching.countrylist

import com.adammcneilly.apollocaching.models.CountryOverview

data class CountryListViewState(
    val showLoading: Boolean,
    val showCountries: Boolean,
    val countryOverviews: List<CountryOverview>?
)
