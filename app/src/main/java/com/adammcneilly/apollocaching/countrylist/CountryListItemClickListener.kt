package com.adammcneilly.apollocaching.countrylist

import com.adammcneilly.apollocaching.models.CountryOverview

interface CountryListItemClickListener {
    fun onCountryClicked(countryOverview: CountryOverview)
}
