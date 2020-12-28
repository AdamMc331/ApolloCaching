package com.adammcneilly.apollocaching.countrylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.apollocaching.data.CountryRepository
import com.adammcneilly.apollocaching.models.CountryOverview
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _viewState: MutableLiveData<CountryListViewState> = MutableLiveData()
    val viewState: LiveData<CountryListViewState> = _viewState

    init {
        setInitialViewState()

        viewModelScope.launch {
            val countryOverviews = countryRepository.getCountryList()

            handleCountryOverviewsResponse(countryOverviews)
        }
    }

    private fun handleCountryOverviewsResponse(countryOverviews: List<CountryOverview>) {
        _viewState.value = _viewState.value?.copy(
            showLoading = false,
            showCountries = true,
            countryOverviews = countryOverviews
        )
    }

    private fun setInitialViewState() {
        _viewState.value = CountryListViewState(
            showLoading = true,
            showCountries = false,
            countryOverviews = emptyList()
        )
    }
}
