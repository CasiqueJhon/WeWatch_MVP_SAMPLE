package com.jhoncasique.wewatch_mvp_sample.model

import android.app.Application
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalDataSource(application: Application) {

    private val movieDao: MovieDao
    open val allMovies: Observable<List<Movie>>

    init {
        val bd = LocalDataBase.getInstance((application))
        movieDao = bd.movieDao()
        allMovies = movieDao.all
    }

    fun insert(movie: Movie) {
        Completable.fromAction {
            movieDao.insert(movie)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    fun delete(movie: Movie) {
        Completable.fromAction {
            movieDao.delete(movie.id)
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}