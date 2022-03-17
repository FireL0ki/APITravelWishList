package com.example.travelwishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*


const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

    private val places = mutableListOf<Place>(Place("Loch Ness", "I'm a pluviophile"),
        Place("New York, New York","I wanna wake up in a city that never sleeps"))

    fun getPlaces(): List<Place> {
        return places // smart cast
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {
        // return location in the list that the new item was added

        // could solve check for duplicates with this loop:
//        for (placeName in placeNames) {
//            if (placeName.uppercase() == place.uppercase()) {
//                return -1 // -1
//            }
//        }

        // all() function would return true if all of the things in a list meet a condition
        // any() if any of the things in a list meet a condition (specified in the lambda
        // Place object can't be uppercase(), but its name can -- .name
        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() } ) {
            return -1
        }

        return if (position == null) {
            places.add(place) // currently adds at the end
            places.lastIndex // (return (left out because the full if statement already says return)
        } else {
            places.add(position, place)
            position // return (see above note)
        }

    }

    fun movePlace(from: Int, to: Int) {
        val place = places.removeAt(from)
        places.add(to, place)
        Log.d(TAG, places.toString())
    }

    fun deletePlace(position: Int): Place {
        return places.removeAt(position)
    }

}