package com.ardor.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val Poster: String = "",
    val Title: String = "",
    val Type: String? = "",
    val Year: String? = "",
    val imdbID: String = ""
) : Parcelable
