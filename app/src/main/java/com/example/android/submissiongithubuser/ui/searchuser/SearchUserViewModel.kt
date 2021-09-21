package com.example.android.submissiongithubuser.ui.searchuser

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.submissiongithubuser.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class SearchUserViewModel() : ViewModel() {

    val listUser =  MutableLiveData<ArrayList<User>>()

    fun setUser(query: String, context: Context) {
        val listItems = ArrayList<User>()

        val apiKey = "1e19d4353aaee9bfd841f369ed2c61e04a95392e"
        val url = "https://api.github.com/search/users?q=$query"

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
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val users = list.getJSONObject(i)
                        val user = User()

                        user.username = users.getString("login")
                        user.avatar = users.getString("avatar_url")

                        listItems.add(user)
                    }
                    listUser.postValue(listItems)
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

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}

