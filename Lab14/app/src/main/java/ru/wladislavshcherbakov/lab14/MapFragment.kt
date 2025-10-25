package ru.wladislavshcherbakov.lab14

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class MapFragment : Fragment() {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var cityName: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        val mapText: TextView = view.findViewById(R.id.mapText)
        mapText.text = "${getString(R.string.map_title)}: $cityName\n${getString(R.string.map_coordinates).format(latitude, longitude)}"

        return view
    }

    companion object {
        fun newInstance(lat: Double, lon: Double, name: String): MapFragment {
            val fragment = MapFragment()
            fragment.latitude = lat
            fragment.longitude = lon
            fragment.cityName = name
            return fragment
        }
    }
}