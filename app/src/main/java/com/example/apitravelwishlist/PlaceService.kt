package com.example.apitravelwishlist

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// place is a name for a class that makes request to an API on behalf of an app

interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List<Place>>

    // POST create place
    @POST("places/")
    suspend fun addPlace(@Body place: Place): Response<Place>

    // TODO update place

    // TODO delete place

}