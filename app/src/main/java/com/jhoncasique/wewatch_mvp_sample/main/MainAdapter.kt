package com.jhoncasique.wewatch_mvp_sample.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jhoncasique.wewatch_mvp_sample.R
import com.jhoncasique.wewatch_mvp_sample.model.Movie
import com.jhoncasique.wewatch_mvp_sample.network.RetrofitClient
import com.squareup.picasso.Picasso

class MainAdapter(internal var moviesList: List<Movie>,
                  internal var context: Context):
    RecyclerView.Adapter<MainAdapter.MoviesHolder>() {

    val selectedMovies = HashSet<Movie>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MoviesHolder(view)

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.titleTextView.text = moviesList[position].title
        holder.releaseDateTextView.text = moviesList[position].releaseDate
        if (moviesList[position].posterPath.equals("")) {
            holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
        } else {
            Picasso.get().load(RetrofitClient.TMDB_IMAGE_URL + moviesList[position].posterPath).into(holder.movieImageView)
        }
    }

    inner class MoviesHolder(v: View): RecyclerView.ViewHolder(v) {

        internal var titleTextView: TextView = v.findViewById(R.id.title_textview)
        internal var releaseDateTextView: TextView = v.findViewById(R.id.release_date_textview)
        internal var movieImageView: ImageView = v.findViewById(R.id.movie_imageview)
        internal var checkBox: CheckBox = v.findViewById(R.id.checkbox)

        init {
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(moviesList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(moviesList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(moviesList[adapterPosition])
                }
            }
        }
    }
}