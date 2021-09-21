package com.example.android.submissiongithubuser.repository

import androidx.lifecycle.MutableLiveData
import com.example.android.submissiongithubuser.db.UserDao
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.model.UserDetail

class UserRepository(private val userDao: UserDao) {

    private val favorite: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun insert(user: UserDetail) {
        userDao.insertUser(user)
        favorite.value = true
    }

    suspend fun delete(user: UserDetail) {
        userDao.deleteUser(user)
        favorite.value = false
    }

}