package com.example.android.submissiongithubuser.ui.searchuser

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.adapter.OnItemClickCallback
import com.example.android.submissiongithubuser.adapter.UserAdapter
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.ui.detail.DetailUserActivity
import com.example.android.submissiongithubuser.util.Const.Companion.STATE_FALSE
import com.example.android.submissiongithubuser.util.Const.Companion.STATE_TRUE
import kotlinx.android.synthetic.main.activity_search_user.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchUserActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchUserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)

        showTextSearch(true)
        supportActionBar?.elevation = 0F

        setupViewModel(savedInstanceState)
        setupRecyclerview()
        setupSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLanguage -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }
        }
        return true
    }

    private fun setupSearchView() {
        searchViewUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                showTextSearch(false)
                viewModel.setUser(query, this@SearchUserActivity)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(SearchUserViewModel::class.java)
        viewModel.getUser().observe(this, Observer {
            if (it != null) {
                userAdapter.setData(it)
                if (savedInstanceState != null) {
                    showLoading(savedInstanceState.getBoolean(STATE_FALSE))
                    showTextSearch(savedInstanceState.getBoolean(STATE_FALSE))
                } else {
                    showLoading(false)
                }
            }
            if (it.isEmpty()) {
                if (savedInstanceState != null) {
                    showTextSearch(savedInstanceState.getBoolean(STATE_TRUE))
                } else {
                    showTextSearch(true)
                }
            }
        })
    }

    private fun setupRecyclerview() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        rvUser.adapter = userAdapter
        rvUser.setHasFixedSize(true)

        userAdapter.setOnItemClickCallback(object :
            OnItemClickCallback {
            override fun onItemClicked(user: User) {
                selectedData(user)
            }
        })
    }

    private fun selectedData(user: User) {
        val dataUser = User(
            user.username,
            user.name,
            user.avatar,
            user.company,
            user.location,
            user.followers,
            user.following
        )

        startActivity<DetailUserActivity>(DetailUserActivity.EXTRA_DATA to dataUser)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showTextSearch(state: Boolean) {
        if (state) {
            tvPleaseSearch.visibility = View.VISIBLE
        } else {
            tvPleaseSearch.visibility = View.GONE
        }
    }
}

