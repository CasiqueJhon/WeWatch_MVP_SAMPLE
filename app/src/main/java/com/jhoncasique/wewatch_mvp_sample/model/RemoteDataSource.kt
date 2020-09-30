package com.jhoncasique.wewatch_mvp_sample.model

import com.jhoncasique.wewatch_mvp_sample.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteDataSource {

    fun searchResultsObservable(query: String): Observable<TmdbResponse> {
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}