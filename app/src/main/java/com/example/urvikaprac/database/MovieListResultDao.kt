package com.example.urvikaprac.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieListResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieListResult: List<MovieListResult>)

    @Query("SELECT * FROM movie_list_result WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieListResult

    @Query("SELECT * FROM movie_list_result")
    suspend fun getAllMovies(): List<MovieListResult>

    @Query("SELECT COUNT(*) FROM movie_list_result")
    suspend fun getCount(): Int
}