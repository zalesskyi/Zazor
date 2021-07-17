package com.example.zazor.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zazor.data.storage.models.PhotoDb

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePhoto(photo: PhotoDb)

    @Query("SELECT * FROM photos")
    suspend fun getAll(): List<PhotoDb>

    @Query("SELECT * FROM photos ORDER BY date DESC LIMIT 1")
    suspend fun getLast(): List<PhotoDb>
}