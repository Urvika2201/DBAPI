package com.example.urvikaprac.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urvikaprac.MyApp
import com.example.urvikaprac.adapter.CartAdapter
import com.example.urvikaprac.databinding.ActivityCartBinding
import com.example.urvikaprac.modelclass.BookingSeatModel
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        getCartData()

    }

    fun getCartData() {
        lifecycleScope.launch {
            val count = MyApp.database.movieBookingDao().getBookingSeatCount()
            if (count > 0) {
                setDataList(MyApp.database.movieBookingDao().getAllBookingSeats())
            } else {
                Toast.makeText(this@CartActivity, "Data not available", Toast.LENGTH_SHORT)
                // Database is empty
            }
        }
    }

    fun setDataList(allBookingSeats: List<BookingSeatModel>) {
        binding.rvCartList.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(allBookingSeats)
        binding.rvCartList.adapter = adapter
    }
}