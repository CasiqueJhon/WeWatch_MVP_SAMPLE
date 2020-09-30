package com.jhoncasique.wewatch_mvp_sample.main

import com.jhoncasique.wewatch_mvp_sample.model.Movie

class MainContract {

    interface PresenterInterface {
        fun getMovieList()
        fun onDeleteTapped(selectedMovie: HashSet<Movie>)
        fun stop()
    }

    interface ViewInterface {
        fun displayMovies(movieList: List<Movie>)
        fun displayMovies()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}