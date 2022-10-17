package com.ardor.data.remote.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardor.data.remote.model.Search
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM ${Search.TABLE_NAME}")
    fun getAll() : Flow<List<Search>>

    @Query("DELETE FROM ${Search.TABLE_NAME} WHERE ${Search.PRIMARY_KEY} = :id ")
    fun delete(id : String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : Search)
}