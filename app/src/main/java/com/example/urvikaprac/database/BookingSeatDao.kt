package com.example.urvikaprac.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.urvikaprac.modelclass.BookingSeatModel

@Dao
interface BookingSeatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookingSeat(bookingSeat: BookingSeatModel)

    @Query("SELECT * FROM booking_seat WHERE movieId = :id")
    suspend fun getBookingSeatById(id: Int): BookingSeatModel?

    @Query("SELECT * FROM booking_seat")
    suspend fun getAllBookingSeats(): List<BookingSeatModel>


    @Query("SELECT COUNT(*) FROM booking_seat")
    suspend fun getBookingSeatCount(): Int


    @Delete
    suspend fun deleteBookingSeat(bookingSeat: BookingSeatModel)
}