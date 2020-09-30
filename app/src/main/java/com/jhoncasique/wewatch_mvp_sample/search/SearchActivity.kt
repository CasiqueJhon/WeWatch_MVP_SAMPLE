package com.jhoncasique.wewatch_mvp_sample.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhoncasique.wewatch_mvp_sample.R

class SearchActivity : AppCompatActivity() {

    private lateinit var searchResultRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var query: String

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        searchResultRecyclerView = findViewById(R.id.search_results_recyclerview)
        adapter = SearchAdapter(arrayListOf(), this@SearchActivity, itemListener)
        setupViews()
    }

    private fun setupViews() {
        searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
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