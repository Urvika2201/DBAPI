package com.example.urvikaprac.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.urvikaprac.modelclass.BookingSeatModel

@Database(entities = [MovieListResult::class, MovieDetailsModel::class,BookingSeatModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieListResultDao(): MovieListResultDao

    abstract fun movieDetailsDao(): MovieDetailsDao

    abstract fun movieBookingDao(): BookingSeatDao


}