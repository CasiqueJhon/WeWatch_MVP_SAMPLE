package com.jhoncasique.wewatch_mvp_sample.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jhoncasique.wewatch_mvp_sample.R
import com.jhoncasique.wewatch_mvp_sample.model.LocalDataSource
import com.jhoncasique.wewatch_mvp_sample.network.RetrofitClient.TMDB_IMAGE_URL
import com.jhoncasique.wewatch_mvp_sample.search.SearchActivity
import com.squareup.picasso.Picasso

class AddMovieActivity : AppCompatActivity(), AddMoviesContract.ViewInterface {

    private lateinit var addMoviePresenter: AddMoviesPresenter
    private lateinit var titleEditText: EditText
    private lateinit var releaseDateEditText: EditText
    private lateinit var movieImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setupPresenter()
        setupViews()
    }

    private fun setupViews() {
        titleEditText = findViewById(R.id.movie_title)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)
    }

    private fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        addMoviePresenter = AddMoviesPresenter(this@AddMovieActivity, dataSource)
    }

    override fun returnToMain() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@AddMovieActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    //search onClick
    fun goToSearchMovieActivity(view: View) {
        val title = titleEditText.text.toString()
        val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, title)
        startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    //addMovie onClick
    fun onClickAddMovie(view: View) {
        val title = titleEditText.text.toString()
        val releaseDate = releaseDateEditText.text.toString()
        val posterPath = if (movieImageView.tag != null) movieImageView.tag.toString() else ""
        addMoviePresenter.addMovie(title, releaseDate, posterPath)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@AddMovieActivity.runOnUiThread {
            titleEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_TITLE))
            releaseDateEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE))
            movieImageView.tag = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)
            Picasso.get().load(TMDB_IMAGE_URL + data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)).into(movieImageView)
        }
    }

    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }
}