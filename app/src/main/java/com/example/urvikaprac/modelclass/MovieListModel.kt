package com.example.urvikaprac.modelclass

import com.example.urvikaprac.database.MovieListResult
import com.google.gson.annotations.SerializedName

data class MovieListModel(
    val page: Int,
    val results: List<MovieListResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)

