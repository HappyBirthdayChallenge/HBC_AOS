package com.inha.hbc.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SignupData (
    var id: String? = null,
    var pw: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var year: Int? = null,
    var month: Int? = null,
    var day: Int? = null
) : Parcelable
