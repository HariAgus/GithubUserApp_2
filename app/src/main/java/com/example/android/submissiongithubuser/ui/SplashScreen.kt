package com.example.android.submissiongithubuser.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.android.submissiongithubuser.R
import com.example.android.submissiongithubuser.ui.searchuser.SearchUserActivity
import com.example.android.submissiongithubuser.util.Const.Companion.TIME_MOVE_ACTIVITY
import org.jetbrains.anko.startActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity<SearchUserActivity>()
            finish()
        }, TIME_MOVE_ACTIVITY)

    }
}