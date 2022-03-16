package com.example.travelwishlist

import java.text.SimpleDateFormat
import java.util.*

class Place(val name: String, val dateAdded: Date = Date()) {

    fun formattedDate(): String {
        // you can tweak the date format however you like
        return SimpleDateFormat("EEE, d, MMM, yyyy", Locale.getDefault()).format(dateAdded)
    }

    override fun toString(): String {
        return "$name ${formattedDate()}"
    }

}