package com.example.urvikaprac.webservice

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper  {

    val baseUrl = "https://api.themoviedb.org/3/movie/"


    val client = OkHttpClient.Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) // Replace with your base URL
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}