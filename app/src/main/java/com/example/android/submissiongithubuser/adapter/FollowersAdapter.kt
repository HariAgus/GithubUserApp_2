package com.example.android.submissiongithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val mFollowerData = ArrayList<User>()

    fun setDataFollowers(item: ArrayList<User>) {
        mFollowerData.clear()
        mFollowerData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int = mFollowerData.size

    override fun onBindViewHolder(holder: FollowersAdapter.ViewHolder, position: Int) {
        holder.bind(mFollowerData[position])
    }

    inner class ViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {
        fun bind(user: User) {
                itemView.tvName.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(itemView.circleImgUser)

        }
    }
}