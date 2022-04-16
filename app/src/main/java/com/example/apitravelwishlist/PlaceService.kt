package com.example.apitravelwishlist

import retrofit2.Response
import retrofit2.http.GET

// place is a name for a class that makes request to an API on behalf of an app

interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List<Place>>

    // TODO POST create place

    // TODO update place

    // TODO delete place

}