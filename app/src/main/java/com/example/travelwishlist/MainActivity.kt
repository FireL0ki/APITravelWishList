package com.example.travelwishlist

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

class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {

    private lateinit var newPlaceEditText: EditText
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
        newPlaceEditText = findViewById(R.id.new_place_name)

        val places = placesViewModel.getPlaces() // list of place objects

        // set up and connect the adapter
        placesRecyclerAdapter = PlaceRecyclerAdapter(places, this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        val itemSwipeListner = OnListItemSwipeListener(this)
        val ith = ItemTouchHelper(itemSwipeListner)
        ith.attachToRecyclerView(placeListRecyclerView)
        // or write it:
        // ItemTouchHelper(listener).attachToRecyclerView(placeListRecyclerView)


        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }
    }

    private fun addNewPlace() {
        // read data from edit text & add to the list (ask viewModel to add to the list)
        val name = newPlaceEditText.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter a place name", Toast.LENGTH_SHORT).show()
        } else {
            val newPlace = Place(name)
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
        newPlaceEditText.text.clear()
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onListItemClicked(place: Place) {
//        Toast.makeText(this, "$place.name map icon was clicked", Toast.LENGTH_SHORT).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }

    override fun onListItemMoved(from: Int, to: Int) {
        TODO("Not yet implemented")
    }

    override fun onListItemDeleted(position: Int) {
        TODO("Not yet implemented")
    }

}