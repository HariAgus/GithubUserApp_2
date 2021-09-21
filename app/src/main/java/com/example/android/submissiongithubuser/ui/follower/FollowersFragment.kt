package com.example.android.submissiongithubuser.ui.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.adapter.FollowersAdapter
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.ui.detail.DetailUserActivity
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private lateinit var followerAdapter: FollowersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent?.getParcelableExtra<User>(
            DetailUserActivity.EXTRA_DATA
        )
        val userName = intent?.username

        setupFollowerViewModel(userName)
        setupRecyclerView()
    }

    private fun setupFollowerViewModel(userName: String?) {
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowerViewModel::class.java)

        viewModel.setFollowerUser(userName, activity!!)
        viewModel.getFollower().observe(activity!!, Observer {
            if (it != null) {
                followerAdapter.setDataFollowers(it)
            }
        })
    }

    private fun setupRecyclerView() {
        followerAdapter = FollowersAdapter()
        followerAdapter.notifyDataSetChanged()

        rvFollowers.adapter = followerAdapter
        rvFollowers.setHasFixedSize(true)
    }

}