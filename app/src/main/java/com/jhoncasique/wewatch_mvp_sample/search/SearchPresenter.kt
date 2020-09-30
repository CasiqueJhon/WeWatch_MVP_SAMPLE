package com.jhoncasique.wewatch_mvp_sample.search

import android.util.Log
import com.jhoncasique.wewatch_mvp_sample.model.RemoteDataSource
import com.jhoncasique.wewatch_mvp_sample.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter (private var viewInterface: SearchContract.ViewInterface,
                       dataSource: RemoteDataSource):
    SearchContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()
    private val TAG = "Search Presenter"

    val searchResultObservable: (String) -> Observable<TmdbResponse> =
        { query -> dataSource.searchResultsObservable(query) }

    val observer: DisposableObserver<TmdbResponse>
    get() = object : DisposableObserver<TmdbResponse>() {

        override fun onComplete() {
            Log.d(TAG, "Completed")
        }

        override fun onNext(tmdbResopnse: TmdbResponse) {
           Log.d(TAG, "OnNext" + tmdbResopnse.totalResults)
            viewInterface.displayResult(tmdbResopnse)
        }

        override fun onError(error: Throwable) {
            Log.d(TAG, "Error fetching movie data", error)
            viewInterface.displayError("Error fetching movie data")
        }

    }

    override fun getSearchResult(query: String) {
       val searchResultDisposable = searchResultObservable(query)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribeWith(observer)

        compositeDisposable.add(searchResultDisposable)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }


}