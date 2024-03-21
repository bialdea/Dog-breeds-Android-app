package com.example.dog.API


import com.example.dog.model.Dog
import com.example.dog.model.ImagesDog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DogAPI {

    @GET("breeds/list/all")
    fun getDogRasa(): Call<Dog> //obiect Call care va conține datele de răspuns, care este furnizat de biblioteca Retrofit și reprezintă o cerere asincronă către server
//Call<Dog> indică că răspunsul așteptat va fi deserializat într-un obiect de tip Dog.
    companion object { //locul companion object este utilizat pentru a defini metode sau proprietăți statice în interiorul interfeței DogAPI
        private val httpInterceptor = HttpLoggingInterceptor().apply { //utilizat pentru a înregistra informații despre cererile HTTP și răspunsurile lor în scopuri de depanare și monitorizare
            this.level = HttpLoggingInterceptor.Level.BODY //vor fi înregistrate toate detaliile despre cereri și răspunsuri, inclusiv conținutul lor
        }

        private val httpClient = OkHttpClient.Builder().apply { // pentru a configura un client HTTP pentru efectuarea cererilor către serviciul web API
            this.addInterceptor(httpInterceptor) // se adaugă interceptorul httpInterceptor la clientul HTTP pentru a înregistra cererile și răspunsurile
        }.build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create()) //un convertor pentru serializarea și deserializarea datelor JSON între obiectele Kotlin și formatul JSON
            .client(httpClient)  //se specifică clientul HTTP care va fi utilizat pentru a efectua cererile către serviciul web API
            .build()

        fun getInstance(): DogAPI {
            return retrofit.create(DogAPI::class.java) //pentru a crea și returna o instanță a interfeței DogAPI.
        }
    }


    @GET("breed/{breed}/images")
    fun getBreedImages(@Path("breed") breed: String): Call<ImagesDog>
}