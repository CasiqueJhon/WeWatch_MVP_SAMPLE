package com.jhoncasique.wewatch_mvp_sample.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jhoncasique.wewatch_mvp_sample.R
import com.jhoncasique.wewatch_mvp_sample.model.Movie
import com.jhoncasique.wewatch_mvp_sample.network.RetrofitClient
import com.squareup.picasso.Picasso

class SearchAdapter(var movieList: List<Movie>, var context: Context, var listener: SearchActivity.RecyclerItemListener):
    RecyclerView.Adapter<SearchAdapter.SearchMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_details, parent, false)
        val viewHolder = SearchMoviesViewHolder(view)
        view.setOnClickListener { view ->
            listener.onItemClick(view, viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getItemPosition(position: Int): Movie {
        return movieList[position]
    }

    override fun onBindViewHolder(holder: SearchMoviesViewHolder, position: Int) {
        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].releaseDate
        holder.overviewTextView.text = movieList[position].overview

        if (movieList[position].posterPath != null) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieList[position].posterPath).into(holder.movieImageView)
        }

    }

    inner class SearchMoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.title_textview)
        var overviewTextView: TextView = view.findViewById(R.id.release_date_textview)
        var releaseDateTextView: TextView = view.findViewById(R.id.overview_overview)
        var movieImageView: ImageView = view.findViewById(R.id.movie_imageview)

        init {
            view.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition)
            }
        }

    }
}