package ru.wladislavshcherbakov.lab23

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RepositoryAdapter
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupSearchButton()
    }

    private fun setupRecyclerView() {
        adapter = RepositoryAdapter(
            onRepoClick = { url -> openUrlInBrowser(url) },
            onOwnerClick = { url -> openUrlInBrowser(url) }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupSearchButton() {
        val searchButton = findViewById<Button>(R.id.searchButton)
        val searchInput = findViewById<EditText>(R.id.searchInput)

        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            if (query.isNotEmpty()) {
                searchRepositories(query)
            }
        }
    }

    private fun searchRepositories(query: String) {
        val request = Request.Builder()
            .url("https://api.github.com/search/repositories?q=$query")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.error_network,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val jsonResponse = response.body?.string()
                        val searchResult = Gson().fromJson(
                            jsonResponse,
                            GitHubSearchResponse::class.java
                        )

                        runOnUiThread {
                            adapter.updateData(searchResult.items)
                        }
                    }
                }
            }
        })
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}