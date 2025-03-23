package ru.wladislavshcherbakov.lab13

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CityListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CityAdapter(Common.cities) { city ->
            val resultIntent = Intent()
            resultIntent.putExtra("selectedCity", city.title)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        recyclerView.adapter = adapter
    }
}