package com.ardor.data.remote.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ardor.data.remote.model.Search.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class Search(
    @ColumnInfo(name = "poster")
    val Poster: String = "",
    @ColumnInfo(name = "title")
    val Title: String = "",
    @ColumnInfo(name = "type")
    val Type: String? = "",
    @ColumnInfo(name = "year")
    val Year: String? = "",
    @PrimaryKey
    @ColumnInfo(name = PRIMARY_KEY)
    val imdbID: String = ""
) : Parcelable {
    companion object{
        const val TABLE_NAME = "favoriteMovies"
        const val PRIMARY_KEY = "imdbID"
    }
}
