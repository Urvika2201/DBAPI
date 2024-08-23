package com.example.urvikaprac.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.urvikaprac.R
import com.example.urvikaprac.database.MovieListResult
import com.squareup.picasso.Picasso
import java.util.Locale

class MovieListAdapter(
    private var movies: List<MovieListResult>,
    var onClickListener: OnClickListener
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {



    private val dataset: ArrayList<MovieListResult> = ArrayList()

    fun initializeData(data: List<MovieListResult>) {
        dataset.clear()
        dataset.addAll(data)
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: AppCompatTextView = itemView.findViewById(R.id.txtMovieName)
        val txtMovieReleaseDate: AppCompatTextView = itemView.findViewById(R.id.txtMovieReleaseDate)
        val imgMoviePoster: AppCompatImageView = itemView.findViewById(R.id.imgMoviePoster)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_adapter, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieName.text = "Movie Name:" + movie.title
        holder.txtMovieReleaseDate.text = "Movie ReleaseDate:" + movie.releaseDate

        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie.id)
        }

        val imageUrl = "https://image.tmdb.org/t/p/w500/" + movie.posterPath
        Picasso.get().load(imageUrl).into(holder.imgMoviePoster)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun updateList(newList: List<MovieListResult>) {
        movies = newList
        notifyDataSetChanged()
    }
}