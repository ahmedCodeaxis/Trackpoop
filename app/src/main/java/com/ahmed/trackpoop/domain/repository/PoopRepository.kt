package com.ahmed.trackpoop.domain.repository

import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.domain.model.Poop

interface PoopRepository {
    suspend fun getPoop(): Response<List<Poop>?>
    /*suspend fun getPoopById(id: String)
    suspend fun insertPoop()
    suspend fun deletePoop()
    suspend fun updatePoop()*/
}