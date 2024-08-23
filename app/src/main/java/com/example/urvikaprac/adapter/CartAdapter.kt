package com.example.urvikaprac.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.urvikaprac.R
import com.example.urvikaprac.modelclass.BookingSeatModel

class CartAdapter(

    private val cartList: List<BookingSeatModel>?
) : RecyclerView.Adapter<CartAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_list_adapter, parent, false)
        return CartAdapter.MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.MovieViewHolder, position: Int) {

        val cartData = cartList?.get(position)
        if (cartData != null) {
            holder.movieName.text = "Movie Name: " + cartData.movieName
            holder.txtNoOFSeat.text = "No. Of Seat: " + cartData.seatNo
            holder.txtAMount.text = "No. Of Seat: " + cartData.perseatcharge
        }
    }

    override fun getItemCount(): Int {
        return cartList!!.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: AppCompatTextView = itemView.findViewById(R.id.txtMovieName)
        val txtNoOFSeat: AppCompatTextView = itemView.findViewById(R.id.txtNoOFSeat)
        val txtAMount: AppCompatTextView = itemView.findViewById(R.id.txtAMount)

    }
}