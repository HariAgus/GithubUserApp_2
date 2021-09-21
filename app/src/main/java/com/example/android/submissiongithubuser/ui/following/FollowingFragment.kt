package com.example.android.submissiongithubuser.ui.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.adapter.FollowingAdapter
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.ui.detail.DetailUserActivity
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private lateinit var followingAdapter: FollowingAdapter

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
            .get(FollowingViewModel::class.java)

        viewModel.setFollowingUser(userName, activity!!)
        viewModel.getFollowing().observe(activity!!, Observer {
            if (it != null) {
                followingAdapter.setDataFollowing(it)
            }
        })
    }

    private fun setupRecyclerView() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        rvFollowing.adapter = followingAdapter
        rvFollowing.setHasFixedSize(true)
    }

}