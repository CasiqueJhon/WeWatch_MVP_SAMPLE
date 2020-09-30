package com.jhoncasique.wewatch_mvp_sample.main

import android.util.Log
import com.jhoncasique.wewatch_mvp_sample.model.LocalDataSource
import com.jhoncasique.wewatch_mvp_sample.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(private var viewInterface: MainContract.ViewInterface,
                    private var dataSource: LocalDataSource):
    MainContract.PresenterInterface {

    private val compositeDiposbale = CompositeDisposable()
    private val TAG = "MainPresenter"
    val moviesObservable: Observable<List<Movie>>
    get() = dataSource.allMovies

    val observer: DisposableObserver<List<Movie>>
    get() = object : DisposableObserver<List<Movie>>() {

        override fun onComplete() {
           Log.d(TAG, "Completed")
        }

        override fun onNext(movieList: List<Movie>) {
            if (movieList == null || movieList.isEmpty()) {
                viewInterface.displayMovies()
            } else {
                viewInterface.displayMovies(movieList)
            }
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Error fetching movie list")
        }

    }

    override fun getMovieList() {
        val moviesDisposable = moviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDiposbale.add(moviesDisposable)
    }

    override fun onDeleteTapped(selectedMovie: HashSet<Movie>) {
       for (movie in selectedMovie) {
           dataSource.delete(movie)
       }
    }

    override fun stop() {
        compositeDiposbale.clear()
    }
}