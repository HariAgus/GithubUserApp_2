package com.example.android.submissiongithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listUser = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(item: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(item)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.tvName.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(itemView.circleImgUser)

            itemView.circleImgUser.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

        }
    }
}

interface OnItemClickCallback{
    fun onItemClicked(user: User)
}