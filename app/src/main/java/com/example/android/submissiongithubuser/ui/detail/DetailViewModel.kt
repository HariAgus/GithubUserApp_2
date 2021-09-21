package com.example.android.submissiongithubuser.ui.detail

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.android.submissiongithubuser.db.UserDao
import com.example.android.submissiongithubuser.db.UserDatabase
import com.example.android.submissiongithubuser.model.User
import com.example.android.submissiongithubuser.model.UserDetail
import com.example.android.submissiongithubuser.repository.UserRepository
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.launch
import org.json.JSONObject

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    val listUserDetail =  MutableLiveData<UserDetail>()
    private var userDao: UserDao = UserDatabase.getDatabase(app).userDao()
    private var userRepository: UserRepository

    init {
        userRepository = UserRepository(userDao)
    }

    fun setDetailUser(username: String?, context: Context) {
        val apiKey = "1e19d4353aaee9bfd841f369ed2c61e04a95392e"
        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody!!)
                    val responseObject = JSONObject(result)
                    val userDetail = UserDetail()

                    userDetail.username = responseObject.getString("login")
                    userDetail.name = responseObject.getString("name")
                    userDetail.avatar = responseObject.getString("avatar_url")
                    userDetail.company = responseObject.getString("company")
                    userDetail.location = responseObject.getString("location")
                    userDetail.followers = responseObject.getInt("followers")
                    userDetail.following = responseObject.getInt("following")

                    listUserDetail.postValue(userDetail)
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(context, "Failed to connect $statusCode", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addFavorite(user: UserDetail) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun deleteFavorite(user: UserDetail) = viewModelScope.launch {
        userRepository.delete(user)
    }

//
//    fun saveUser(user: User) = viewModelScope.launch {
//        userRepository.upset(user)
//    }
//
//    fun getSavedUser() {
//        db.getUserDao().getAllUser()
//    }
//
//
    fun getDetailUser(): LiveData<UserDetail> {
        return listUserDetail
    }

}