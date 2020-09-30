package com.jhoncasique.wewatch_mvp_sample.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jhoncasique.wewatch_mvp_sample.R
import com.jhoncasique.wewatch_mvp_sample.add.AddMovieActivity
import com.jhoncasique.wewatch_mvp_sample.model.LocalDataSource
import com.jhoncasique.wewatch_mvp_sample.model.Movie

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var noMoviesLayout: LinearLayout
    private lateinit var mainPresenter : MainPresenter
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.getMovieList()
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.stop()
    }

    private fun setupViews() {
        moviesRecyclerView = findViewById(R.id.movies_recyclerview)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf(), this@MainActivity)
        moviesRecyclerView.adapter = adapter
        fab = findViewById(R.id.no_movies_layout)
        supportActionBar?.title = getString(R.string.movies_to_watch)
    }

    private fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        mainPresenter = MainPresenter(this, dataSource)
    }

    override fun displayMovies(movieList: List<Movie>) {
        adapter.moviesList = movieList
        adapter.notifyDataSetChanged()

        moviesRecyclerView.visibility = INVISIBLE
        noMoviesLayout.visibility = VISIBLE
    }

    override fun displayMovies() {
        Log.d(TAG, "No movies to display")

        moviesRecyclerView.visibility = VISIBLE
        noMoviesLayout.visibility = INVISIBLE
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    //fab onClick
    fun goToAddMovieActivity(view: View) {
        val intent = Intent(this@MainActivity, AddMovieActivity::class.java)
        startActivityForResult(intent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            displayMessage("Movie successfully added")
        } else {
            displayError("Movie could not be added")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            mainPresenter.onDeleteTapped(adapter.selectedMovies)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
    }
}