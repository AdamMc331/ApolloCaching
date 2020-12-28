package com.adammcneilly.apollocaching.countrydetail

import com.adammcneilly.apollocaching.models.CountryDetail

data class CountryDetailViewState(
    val showLoading: Boolean,
    val showDetail: Boolean,
    private val countryDetail: CountryDetail?
) {
    val countryName: String?
        get() = countryDetail?.name

    val countryContinent: String?
        get() = countryDetail?.continentName

    val countryCapital: String?
        get() = countryDetail?.capital

    val countryEmoji: String?
        get() = countryDetail?.emoji
}
