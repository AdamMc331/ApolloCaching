package com.adammcneilly.apollocaching.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryOverview(
    val code: String,
    val name: String
) : Parcelable
