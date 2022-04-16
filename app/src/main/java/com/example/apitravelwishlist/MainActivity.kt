package com.example.apitravelwishlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwishlist.R
import java.util.*

class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {

    private lateinit var newNameEditText: EditText
    private lateinit var newReasonEditText: EditText
    private lateinit var addNewPlaceButton: Button
    private lateinit var placeListRecyclerView: RecyclerView

    // reference adapter
    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter

    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        newNameEditText = findViewById(R.id.new_place_name)
        newReasonEditText = findViewById(R.id.enter_reason)

        val places = placesViewModel.getPlaces() // list of place objects

        // set up and connect the adapter
        placesRecyclerAdapter = PlaceRecyclerAdapter(places, this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        val itemSwipeListener = OnListItemSwipeListener(this)
        val ith = ItemTouchHelper(itemSwipeListener)
        ith.attachToRecyclerView(placeListRecyclerView)
        // or write it:
        // ItemTouchHelper(listener).attachToRecyclerView(placeListRecyclerView)


        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }
    }

    private fun addNewPlace() {
        // read data from edit text & add to the list (ask viewModel to add to the list)
        val name = newNameEditText.text.toString().trim()
        val reason = newReasonEditText.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter a place name", Toast.LENGTH_SHORT).show()
        } else {
            val newPlace = Place(name, reason)
            val positionAdded = placesViewModel.addNewPlace(newPlace)
            if (positionAdded == -1) {
                Toast.makeText(this, "You already added that place", Toast.LENGTH_SHORT).show()
            } else {
                placesRecyclerAdapter.notifyItemInserted(positionAdded)
                clearForm()
                hideKeyboard()
            }
        }
    }

    private fun clearForm() {
        newNameEditText.text.clear()
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onMapRequestButtonClicked(place: Place) {
//        Toast.makeText(this, "$place.name map icon was clicked", Toast.LENGTH_SHORT).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }

    override fun onStarredStatusChanged(place: Place, isStarred: Boolean) {
        place.starred = isStarred
        placesViewModel.updatePlace(place)
    }

//    override fun onListItemMoved(from: Int, to: Int) {
//        placesViewModel.movePlace(from, to)
//        placesRecyclerAdapter.notifyItemMoved(from, to)
//    }

    override fun onListItemDeleted(position: Int) {
        val deletedPlace = placesViewModel.deletePlace(position)
        placesRecyclerAdapter.notifyItemRemoved(position)

//        // Snackbar is a more updated version of a toast, may also allow an action to take place
//        Snackbar.make(findViewById(R.id.wishlist_container), getString(R.string.place_deleted, deletedPlace.name), 5000)
//            .setActionTextColor(resources.getColor(R.color.red))
//            .setBackgroundTint(resources.getColor(R.color.dark_grey))
//            .setAction(getString(R.string.undo)) {  // displays an "UNDO" button
//                placesViewModel.addNewPlace(deletedPlace, position)
//                placesRecyclerAdapter.notifyItemInserted(position)
//            }
//            .show()
    }

}