package com.example.android.submissiongithubuser.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.model.UserDetail

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserDetail)

    @Delete
    suspend fun deleteUser(user: UserDetail): Int

}