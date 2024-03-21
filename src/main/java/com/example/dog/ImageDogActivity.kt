package com.example.dog

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dog.adapters.AdaptorImaginiList
import com.example.dog.API.DogAPI
import com.example.dog.model.ImagesDog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageDogActivity : AppCompatActivity() {

    private var currentImages = listOf<String>()
    private var allImages = listOf<String>()
    private var currentIndex = 0
    private lateinit var adapter: AdaptorImaginiList
    private var favorites = mutableSetOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagini)

        val selectedBreed = intent.getStringExtra("breed") ?: return
        fetchBreedImages(selectedBreed)

        val loadMoreButton = findViewById<Button>(R.id.loadMoreButton)
        loadMoreButton.setOnClickListener {
            loadMoreImages()
        }
        val listView = findViewById<ListView>(R.id.listaImaginiListView)
        registerForContextMenu(listView)

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, DogActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchBreedImages(breed: String) {
        DogAPI.getInstance().getBreedImages(breed).enqueue(object : Callback<ImagesDog> {
            override fun onResponse(call: Call<ImagesDog>, response: Response<ImagesDog>) {
                if (response.isSuccessful) {
                    allImages = response.body()?.message ?: return
                    loadMoreImages()
                }
            }

            override fun onFailure(call: Call<ImagesDog>, t: Throwable) {
                // Gestionează eșecul apelului API
            }
        })
    }

    private fun loadMoreImages() {
        val nextIndex = currentIndex + 6
        currentImages = currentImages + allImages.subList(currentIndex, nextIndex.coerceAtMost(allImages.size))
        currentIndex = nextIndex

        displayImages(currentImages)

        if (currentIndex >= allImages.size) {
            findViewById<Button>(R.id.loadMoreButton).visibility = View.GONE
        }
    }



    private fun displayImages(imageUrls: List<String>) {
        val listView = findViewById<ListView>(R.id.listaImaginiListView)
        adapter = AdaptorImaginiList(this, imageUrls, favorites)
        listView.adapter = adapter
    }


    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.meniu_contextual_favorite, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val selectedImageUrl = adapter.getItem(position)

        when (item.itemId) {
            R.id.add_favourite -> {
                favorites.add(selectedImageUrl)
                adapter.notifyDataSetChanged() // Reîmprospătează adaptorul
            }
            R.id.remove_favourite -> {
                favorites.remove(selectedImageUrl)
                adapter.notifyDataSetChanged() // Reîmprospătează adaptorul
            }
        }

        return super.onContextItemSelected(item)
    }

}

