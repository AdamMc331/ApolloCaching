package com.adammcneilly.apollocaching.di

import com.adammcneilly.apollocaching.countrydetail.CountryDetailViewModel
import com.adammcneilly.apollocaching.countrylist.CountryListViewModel
import com.adammcneilly.apollocaching.models.CountryOverview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CountryListViewModel(
            countryRepository = get()
        )
    }

    viewModel { (countryOverview: CountryOverview) ->
        CountryDetailViewModel(
            countryOverview = countryOverview,
            countryRepository = get()
        )
    }
}
