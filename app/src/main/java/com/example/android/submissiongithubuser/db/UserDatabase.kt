package com.example.android.submissiongithubuser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.model.UserDetail

@Database(entities = [UserDetail::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val mInstance = INSTANCE
            if (mInstance != null) return mInstance

            synchronized(UserDatabase::class) {
                val mBuilder = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db.db"
                ).build()
                INSTANCE = mBuilder
                return mBuilder
            }
        }
    }
    abstract fun userDao(): UserDao
}
