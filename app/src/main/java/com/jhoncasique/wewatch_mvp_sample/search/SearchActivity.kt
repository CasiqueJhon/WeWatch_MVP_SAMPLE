package com.jhoncasique.wewatch_mvp_sample.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhoncasique.wewatch_mvp_sample.R
import com.jhoncasique.wewatch_mvp_sample.model.RemoteDataSource
import com.jhoncasique.wewatch_mvp_sample.model.TmdbResponse

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {

    private lateinit var searchPresenter: SearchPresenter
    private lateinit var searchResultRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        searchResultRecyclerView = findViewById(R.id.search_results_recyclerview)
        adapter = SearchAdapter(arrayListOf(), this@SearchActivity, itemListener)
        searchResultRecyclerView.adapter = adapter
        noMoviesTextView = findViewById(R.id.no_movies_textview)
        progressBar = findViewById(R.id.progress_bar)

        val intent = intent
        query = intent.getStringExtra(SEARCH_QUERY).toString()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        progressBar.visibility = VISIBLE
        setupPresenter()
        searchPresenter.getSearchResult(query)

    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    private fun setupViews() {
        searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        searchPresenter = SearchPresenter(this, dataSource)
    }

    override fun displayResult(tmdbResponse: TmdbResponse) {
        progressBar.visibility = INVISIBLE
        if (tmdbResponse.totalResults == null || tmdbResponse.totalResults == 0) {
            searchResultRecyclerView.visibility = INVISIBLE
            noMoviesTextView.visibility = VISIBLE
        } else {
            adapter.movieList = tmdbResponse.results ?: arrayListOf()
            adapter.notifyDataSetChanged()

            searchResultRecyclerView.visibility = VISIBLE
            noMoviesTextView.visibility = INVISIBLE
        }
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@SearchActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    companion object {

        val SEARCH_QUERY = "searchQuery"
        val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */

    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {

        override fun onItemClick(view: View, position: Int) {
            val movie = adapter.getItemPosition(position)
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_TITLE, movie.title)
            replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.releaseDate)
            replyIntent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    interface RecyclerItemListener {
        fun onItemClick(view: View, position: Int)
    }
}