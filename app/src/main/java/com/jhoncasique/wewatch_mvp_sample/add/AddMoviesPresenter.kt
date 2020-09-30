package com.jhoncasique.wewatch_mvp_sample.add

import com.jhoncasique.wewatch_mvp_sample.model.LocalDataSource
import com.jhoncasique.wewatch_mvp_sample.model.Movie

class AddMoviesPresenter (private var viewInterface: AddMoviesContract.ViewInterface,
                          private var dataSource: LocalDataSource): AddMoviesContract.PresenterInterface{

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Movie title cannot be empty")
        } else {
            val movie = Movie(title, releaseDate, posterPath)
            dataSource.insert(movie)
            viewInterface.returnToMain()
        }
    }
}