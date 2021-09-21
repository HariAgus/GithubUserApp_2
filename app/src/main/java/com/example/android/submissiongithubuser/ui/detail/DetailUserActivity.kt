package com.example.android.submissiongithubuser.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.adapter.SectionsPagerAdapter
import com.example.android.submissiongithubuser.db.UserDao
import com.example.android.submissiongithubuser.db.UserDatabase
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.model.UserDetail
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var user: UserDetail
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application))
            .get(DetailViewModel::class.java)

        val intent = intent.getParcelableExtra<User>(EXTRA_DATA)
        val getUsername = intent?.username

        val pagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPagerDetail.adapter = pagerAdapter
        tabsLayoutDetail.setupWithViewPager(viewPagerDetail)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail_user)

//        val userRepository = UserRepository(UserDatabase(this))
//        val viewModelFactory = DetailViewModelFactory(application, userRepository)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        setupDetailUser(getUsername.orEmpty())

//        fbFavorite.setOnClickListener {
//            viewModel.saveUser(user)
//            Snackbar.make(it, "Success", Snackbar.LENGTH_SHORT).show()
//        }

        val userDao: UserDao = Room.databaseBuilder(this, UserDatabase::class.java, "user")
            .allowMainThreadQueries()
            .build()
            .userDao()

        val check = userDao.insertUser(user)
        if (check != null) {
            fbFavorite.visibility = View.GONE
        } else {
            fbFavorite
        }

        fbFavorite.setOnClickListener {
            addOrRemoveFavorite()
        }

    }

    private fun addOrRemoveFavorite() {
        if (!isFavorite) {
            viewModel.addFavorite(user)
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.deleteFavorite(user)
        }
    }

    private fun setupDetailUser(getUsername: String) {
        viewModel.setDetailUser(getUsername, this)
        viewModel.getDetailUser().observe(this, Observer {
            tvDetailName.text = it.name
            tvDetailUserName.text = it.username
            tvDetailLocation.text = it.location
            tvDetailCompany.text = it.company
            tvDetailFollowers.text = resources.getString(R.string.followers_, it.followers)
            tvDetailFollowing.text = resources.getString(R.string.following_, it.following)

            Glide.with(this)
                .load(it.avatar)
                .into(circleDetailImg)

            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarDetail.visibility = View.VISIBLE
        } else {
            progressBarDetail.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}