package com.adammcneilly.apollocaching.countrydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.apollocaching.data.CountryRepository
import com.adammcneilly.apollocaching.models.CountryDetail
import com.adammcneilly.apollocaching.models.CountryOverview
import kotlinx.coroutines.launch

class CountryDetailViewModel(
    countryOverview: CountryOverview,
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _viewState: MutableLiveData<CountryDetailViewState> = MutableLiveData()
    val viewState: LiveData<CountryDetailViewState> = _viewState

    init {
        setInitialViewState()

        viewModelScope.launch {
            val countryDetail = countryRepository.getCountryDetail(countryOverview.code)

            handleCountryDetailResponse(countryDetail)
        }
    }

    private fun handleCountryDetailResponse(countryDetail: CountryDetail?) {
        _viewState.value = _viewState.value?.copy(
            showLoading = false,
            showDetail = true,
            countryDetail = countryDetail
        )
    }

    private fun setInitialViewState() {
        _viewState.value = CountryDetailViewState(
            showLoading = true,
            showDetail = false,
            countryDetail = null
        )
    }
}
