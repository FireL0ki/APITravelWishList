package com.example.apitravelwishlist

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRepository {

    private val TAG = "PLACE_REPOSITORY"

    private val baseURL = "https://place-wish-list.herokuapp.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val placeService = retrofit.create(PlaceService::class.java)

    suspend fun getAllPlaces(): List<Place> {
        try {
            val response = placeService.getAllPlaces()

            if (response.isSuccessful) { // connected, got data back
                val places = response.body() ?: listOf()
                return places
            }
            else {  // connected to server but server sent an error message
                Log.e(TAG, "Error connecting to API server ${response.errorBody()}")
                return listOf()
            }

        } catch (ex: Exception) { // can't connect to server - network error
            Log.e(TAG, "Error connecting to API server", ex)
            return listOf()
        }

    }
}