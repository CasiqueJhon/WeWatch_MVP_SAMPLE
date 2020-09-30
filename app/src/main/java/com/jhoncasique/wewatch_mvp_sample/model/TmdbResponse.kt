package com.jhoncasique.wewatch_mvp_sample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var results : List<Movie>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */

    constructor(page: Int?, totalResults: Int?, totalPages: Int?, results: List<Movie>) {
        this.page = page
        this.totalResults = totalResults
        this.totalPages = totalPages
        this.results = results
    }

}