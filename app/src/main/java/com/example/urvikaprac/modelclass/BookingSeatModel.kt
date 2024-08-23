package com.example.urvikaprac.modelclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_seat")
data class BookingSeatModel(
    @PrimaryKey
    val movieId: Int = 0,
    val seatNo: Int,
    val perseatcharge: Int,
    val movieName: String,
)
