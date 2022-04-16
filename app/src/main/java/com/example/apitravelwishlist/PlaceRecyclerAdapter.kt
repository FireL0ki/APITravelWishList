package com.example.apitravelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// interface is a special kind of a class
interface OnListItemClickedListener {
    fun onMapRequestButtonClicked(place: Place)
    fun onStarredStatusChanged(place: Place, isStarred: Boolean)
}


class PlaceRecyclerAdapter(var places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener ):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>(){

        inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
            fun bind(place: Place) {
                val placeNameTextView: TextView = view.findViewById(R.id.place_name)
                placeNameTextView.text = place.name

                // using <> to enter type (same as above, written differently)
                // adding reason to place, to show up in recycler view
                val placeReasonTextView = view.findViewById<TextView>(R.id.reason_added)
                placeReasonTextView.text = place.reason

                // find map icons
                val mapIcon: ImageView = view.findViewById(R.id.map_icon)
                mapIcon.setOnClickListener {
                    onListItemClickedListener.onMapRequestButtonClicked(place)
                }

                val starCheck = view.findViewById<CheckBox>(R.id.star_check)
                starCheck.setOnClickListener(null)
                starCheck.isChecked = place.starred
                starCheck.setOnClickListener {
                    onListItemClickedListener.onStarredStatusChanged(place, starCheck.isChecked)
                }

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

    // what's the total number of items in the list
    override fun getItemCount(): Int {
        return places.size
    }


}