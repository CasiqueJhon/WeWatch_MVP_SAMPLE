package com.jhoncasique.wewatch_mvp_sample.add

class AddMoviesContract {

    interface PresenterInterface {
        fun addMovie(title: String, releaseDate: String, posterPath: String)
    }

    interface ViewInterface {
        fun returnToMain()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}