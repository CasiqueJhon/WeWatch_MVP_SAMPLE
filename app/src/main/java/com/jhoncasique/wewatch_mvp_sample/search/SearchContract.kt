package com.jhoncasique.wewatch_mvp_sample.search

import com.jhoncasique.wewatch_mvp_sample.model.TmdbResponse

class SearchContract {

    interface PresenterInterface {
        fun getSearchResult(query: String)
        fun stop()
    }

    interface ViewInterface {
        fun displayResult(tmdbResponse: TmdbResponse)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}