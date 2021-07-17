package com.example.zazor.data.models

import com.example.zazor.data.storage.models.PhotoDb
import org.joda.time.DateTime

data class Photo(val path: String,
                 val name: String,
                 val date: DateTime,
                 val address: String? = null,
                 val lat: Double? = null,
                 val lng: Double? = null)

fun Photo.toDb(): PhotoDb =
      PhotoDb(path, name, date.millis, address.orEmpty(), lat ?: 0.0, lng ?: 0.0)