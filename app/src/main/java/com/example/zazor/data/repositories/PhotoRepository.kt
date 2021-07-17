package com.example.zazor.data.repositories

import com.example.zazor.data.models.Photo
import com.example.zazor.data.models.toDb
import com.example.zazor.data.storage.dao.PhotosDao
import com.example.zazor.data.storage.models.toDomain

interface PhotoRepository {

    suspend fun savePhoto(photo: Photo)

    suspend fun getPhotos(): List<Photo>

    suspend fun getLastPhoto(): Photo?
}

class PhotoRepositoryImpl(private val dao: PhotosDao) : PhotoRepository {

    override suspend fun savePhoto(photo: Photo) =
          dao.savePhoto(photo.toDb())

    override suspend fun getPhotos(): List<Photo> =
          dao.getAll().map { it.toDomain() }

    override suspend fun getLastPhoto(): Photo? =
        dao.getLast().firstOrNull()?.toDomain()
}