package com.example.urvikaprac.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetailsModel)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetailsById(movieId: Int): MovieDetailsModel

    @Query("SELECT COUNT(*) FROM movie_details")
    suspend fun getMovieDetailsCount(): Int
}