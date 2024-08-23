package com.example.urvikaprac.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.urvikaprac.MyApp
import com.example.urvikaprac.adapter.MovieListAdapter
import com.example.urvikaprac.common.commonclass
import com.example.urvikaprac.database.DatabaseProvider
import com.example.urvikaprac.database.MovieListResult
import com.example.urvikaprac.databinding.ActivityMainBinding
import com.example.urvikaprac.modelclass.MovieListModel
import com.example.urvikaprac.webservice.RetrofitHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
       // val movieDao = DatabaseProvider.getDatabase(this@MainActivity).movieListResultDao()
        /*
        * Movie List Show.
        * */
        getMovieList("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZDhjNTkwZTdjOWQxMzgxZGFiZDJjZWQzN2FjN2Y1NiIsIm5iZiI6MTcyNDIxOTIwNy4wMTY4NTEsInN1YiI6IjVmYTE0NmJjNjc4MjU5MDAzNjllZWExMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.mHvpCDRxyS1ovBGkThYg72OeSC9K8Kdc-gzhvNDrg04")

        binding.imgCart.setOnClickListener {
            val intent =
                Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getMovieList(userToken: String) {

        if (commonclass.isNetworkAvailable(this@MainActivity)) {
            try {
                val call = RetrofitHelper.apiService.getMovieList("Bearer $userToken", "en-US", "1")
                call.enqueue(object : Callback<MovieListModel> {
                    override fun onResponse(
                        call: Call<MovieListModel>,
                        response: Response<MovieListModel>
                    ) {
                        if (response.body() != null) {
                            val itemData = response.body()!!.results

                            insertMovie(itemData)
                            setDataList(itemData)

                         }

                    }

                    override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                        // Handle network failure here
                        Log.d("TAG", "onFailure:${t.message} ")
                    }
                })
            } catch (e: Exception) {
                Log.d("TAG", "Exception:${e.message} ")
            }
        }else{
            lifecycleScope.launch {
                val count =  MyApp.database.movieListResultDao().getCount()
                if (count > 0) {
                    Toast.makeText(this@MainActivity,"Data available", Toast.LENGTH_SHORT)
                    // Database has data
                    setDataList( MyApp.database.movieListResultDao().getAllMovies())
                } else {
                    Toast.makeText(this@MainActivity,"Data not available", Toast.LENGTH_SHORT)
                    // Database is empty
                }
            }
        }
    }

    fun insertMovie(movieListResult: List<MovieListResult>) {
        lifecycleScope.launch {
            MyApp.database.movieListResultDao().insert(movieListResult)
        }
    }


    fun setDataList(itemData: List<MovieListResult>) {
        val adapter = MovieListAdapter(itemData, object :
            MovieListAdapter.OnClickListener {
            override fun onClick(position: Int) {
                val intent =
                    Intent(
                        this@MainActivity,
                        MovieDetailActivity::class.java
                    )
                intent.putExtra("movieID", position.toString())
                startActivity(intent)
            }
        })
        binding.rvMovieList.adapter = adapter
        val layoutManager = GridLayoutManager(
            this@MainActivity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.rvMovieList.layoutManager = layoutManager

        /*
        * Search Movie name Data
        * */
        adapter.initializeData(itemData)
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = itemData.filter {
                    it.title.contains(newText ?: "", ignoreCase = true)
                }
                adapter.updateList(filteredList)
                return true
            }
        })

    }

}