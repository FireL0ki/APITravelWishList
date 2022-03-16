package com.example.travelwishlist

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

    private val placeNames = mutableListOf<String>("Loch Ness", "Switzerland")

    fun getPlaces(): List<String> {
        return placeNames // smart cast
    }

}