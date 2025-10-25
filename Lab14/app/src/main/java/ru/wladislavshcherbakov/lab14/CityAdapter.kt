package ru.wladislavshcherbakov.lab14

// adapter/CityAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(
    private val cities: List<City>,
    private val onItemClick: (City) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val cityRegion: TextView = itemView.findViewById(R.id.cityRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.cityName.text = city.name  // Было: android:text="@string/city_name"
        holder.cityRegion.text = city.region  // Было: android:text="@string/region_name"

        holder.itemView.setOnClickListener {
            onItemClick(city)
        }
    }

    override fun getItemCount(): Int = cities.size
}