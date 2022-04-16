package com.example.apitravelwishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

//    private val places = mutableListOf<Place>(Place("Loch Ness", "I'm a pluviophile"),
//        Place("New York, New York","I wanna wake up in a city that never sleeps"))

    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>>(listOf<Place>())

    init {
        getPlaces()
    }

    fun getPlaces() {
        viewModelScope.launch {
            val places = placeRepository.getAllPlaces()
            allPlaces.postValue(places)
        }
    }

    fun addNewPlace(place: Place) {
        viewModelScope.launch {
            val newPlace = placeRepository.addPlace(place)
            getPlaces()
        }
    }

    fun deletePlace(place: Place) {
        // TODO
    }

    fun updatePlace(place: Place) {
        // TODO
    }

}