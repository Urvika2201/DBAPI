package com.example.urvikaprac.webservice

import com.example.urvikaprac.database.MovieDetailsModel
import com.example.urvikaprac.modelclass.MovieListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("popular")
    fun getMovieList(
        @Header("Authorization") token: String,
        @Query("language") language: String? = null,
        @Query("page") page: String? = null,
    ): Call<MovieListModel>

    @GET
    fun getMovieDetails(
        @Header("Authorization") token: String,
        @Url url: String
    ): Call<MovieDetailsModel>
}