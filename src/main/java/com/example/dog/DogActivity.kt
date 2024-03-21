package com.example.dog

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.dog.API.DogAPI
import com.example.dog.adapters.AdaptorRaseList
import com.example.dog.model.Dog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogActivity : AppCompatActivity() {

    private lateinit var dogBreedAdapter: AdaptorRaseList
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rase)

        listView = findViewById(R.id.listaRaseListView)
        fetchDogBreeds()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedBreed = dogBreedAdapter.getItem(position)
            val intent = Intent(this, AnimationActivity::class.java).apply {
                putExtra("breed", selectedBreed)
            }
            startActivity(intent)
        }
    }

    private fun fetchDogBreeds() {
        DogAPI.getInstance().getDogRasa().enqueue(object : Callback<Dog> {
            override fun onResponse(call: Call<Dog>, response: Response<Dog>) {
                if (response.isSuccessful) {
                    response.body()?.let { dog ->
                        val breedList = dog.message.keys.toList()
                        dogBreedAdapter = AdaptorRaseList(this@DogActivity, breedList)
                        listView.adapter = dogBreedAdapter
                    }
                }
            }

            override fun onFailure(call: Call<Dog>, t: Throwable) {
                // Gestionează eroarea
            }
        })
    }
}
