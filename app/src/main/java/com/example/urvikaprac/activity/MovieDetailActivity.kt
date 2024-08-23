package com.example.urvikaprac.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.urvikaprac.MyApp
import com.example.urvikaprac.R
import com.example.urvikaprac.common.commonclass
import com.example.urvikaprac.databinding.ActivityMovieDetailBinding
import com.example.urvikaprac.modelclass.BookingSeatModel
import com.example.urvikaprac.database.MovieDetailsModel
import com.example.urvikaprac.webservice.RetrofitHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    var dataMovieID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val extras = intent.extras
        if (extras != null)
            dataMovieID = extras.getString("movieID").toString()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        if (dataMovieID.isNotEmpty()) {
            getMovieDetail(
                dataMovieID,
                "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZDhjNTkwZTdjOWQxMzgxZGFiZDJjZWQzN2FjN2Y1NiIsIm5iZiI6MTcyNDIxOTIwNy4wMTY4NTEsInN1YiI6IjVmYTE0NmJjNjc4MjU5MDAzNjllZWExMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.mHvpCDRxyS1ovBGkThYg72OeSC9K8Kdc-gzhvNDrg04"
            )
        }

        binding.btnBookSeat.setOnClickListener {
            binding.btnBookSeat.visibility = View.GONE
            binding.llBooking.visibility = View.VISIBLE
        }
        binding.btnDoneBookSeat.setOnClickListener {
            val seatNo = binding.edtNoOFSeat.text.toString().trim()
            if (seatNo.isNotEmpty() && seatNo.toInt() > 0) {
                val amout = seatNo.toInt() * 100

                seatBooking(dataMovieID, seatNo, binding.txtMovieDetailName.text.toString(), amout)
            } else {
                Toast.makeText(
                    this@MovieDetailActivity,
                    "Please enter no.of seat.",
                    Toast.LENGTH_SHORT
                )
            }
        }
    }

    private fun getMovieDetail(dataMovieID: String, userToken: String) {
        if (commonclass.isNetworkAvailable(this@MovieDetailActivity)) {
            try {
                val call =
                    RetrofitHelper.apiService.getMovieDetails("Bearer $userToken", dataMovieID)
                call.enqueue(object : Callback<MovieDetailsModel> {
                    override fun onResponse(
                        call: Call<MovieDetailsModel>,
                        response: Response<MovieDetailsModel>
                    ) {
                        if (response.body() != null) {
                            val itemData = response.body()
                            if (itemData != null) {
                                insertMovie(itemData)
                                setDataList(itemData)
                            }
                        }
                    }

                    override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                        // Handle network failure here
                        Log.d("TAG", "onFailure:${t.message} ")
                    }
                })
            } catch (e: Exception) {
                Log.d("TAG", "Exception:${e.message} ")
            }
        } else {
            lifecycleScope.launch {

                val count = MyApp.database.movieDetailsDao().getMovieDetailsCount()
                if (count > 0) {
                    Toast.makeText(this@MovieDetailActivity, "Data available", Toast.LENGTH_SHORT)
                    // Database has data
                    setDataList(
                        MyApp.database.movieDetailsDao().getMovieDetailsById(dataMovieID.toInt())
                    )
                } else {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        "Data not available",
                        Toast.LENGTH_SHORT
                    )
                    // Database is empty
                }
            }
        }
    }

    fun seatBooking(dataMovieID: String, seatNo: String, movieName: String, amout: Int) {

        val bookingSeatsModel =
            BookingSeatModel(dataMovieID.toInt(), seatNo.toInt(), amout, movieName)

        lifecycleScope.launch {
            MyApp.database.movieBookingDao().insertBookingSeat(bookingSeatsModel)
        }
        Toast.makeText(this@MovieDetailActivity, "Seat booking confirm", Toast.LENGTH_SHORT)
        binding.btnBookSeat.visibility = View.VISIBLE
        binding.llBooking.visibility = View.GONE
        onBackPressed()
    }

    fun insertMovie(movieListResult: MovieDetailsModel) {
        lifecycleScope.launch {
            MyApp.database.movieDetailsDao().insertMovieDetails(movieListResult)
        }
    }

    fun setDataList(itemData: MovieDetailsModel) {
        val imageUrl =
            "https://image.tmdb.org/t/p/w500/" + itemData.backdropPath
        Picasso.get().load(imageUrl).placeholder(R.drawable.movie)
            .into(binding.imgMovieDetailPoster)

        val spannableString =
            SpannableString("Movie Name: " + itemData.originalTitle)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(
            boldSpan,
            0,
            11,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.txtMovieDetailName.text = spannableString

        //15
        val spannableStringoverview =
            SpannableString("Movie Overview: " + itemData.overview)
        val boldSpanoverview = StyleSpan(Typeface.BOLD)
        spannableStringoverview.setSpan(
            boldSpanoverview,
            0,
            15,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.txtMovieDetailOverview.text = spannableStringoverview


        val spannableStringreleaseDate =
            SpannableString("Movie ReleaseDate: " + itemData.releaseDate)
        val boldSpanoverreleaseDate = StyleSpan(Typeface.BOLD)
        spannableStringreleaseDate.setSpan(
            boldSpanoverreleaseDate,
            0,
            18,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.txtMovieDetailReleaseDate.text = spannableStringreleaseDate


        val spannableStringstatus =
            SpannableString("Movie ReleaseStatus: " + itemData.status)
        val boldSpanstatus = StyleSpan(Typeface.BOLD)
        spannableStringstatus.setSpan(
            boldSpanstatus,
            0,
            20,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.txtMovieDetailReleaseStatus.text = spannableStringstatus
    }
}