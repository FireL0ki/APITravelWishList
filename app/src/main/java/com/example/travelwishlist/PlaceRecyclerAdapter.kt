package com.example.travelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaceRecyclerAdapter(private val places: List<String>):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>(){

    // ViewHolder class -- data + layout = view holder (made within adapter class, subclass),
    // ViewHolder is going to do the work of combining the view and data

        // ViewHolder manages one view - one 'list item'
        class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
            fun bind(place: String) {
                val placeNameTextView: TextView = view.findViewById(R.id.place_name)
                // another way to put type: (it's what the <> come from in findViewById
                // val placeNameTextView = view.findViewById<TextView>(R.id.place_name)
            }
        }

    // this class needs to be able to do certain jobs (recycler adapter class)
    // how many items in the list?
    // create a ViewHolder for a specific position (what do I display in element [i]?

    // called by the recycler view to create as many view holders that are need to
    // display the list on screen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.place_list_item, parent, false)
        return ViewHolder(view)
    }

    // called by the recycler view to set the data for one list item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        TODO ("Not yet implemented")
    }


}