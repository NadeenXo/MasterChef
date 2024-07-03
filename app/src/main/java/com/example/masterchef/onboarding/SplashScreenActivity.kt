package com.example.masterchef.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.masterchef.auth.LoginManager
import com.example.masterchef.dashboard.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    private val loginManager: LoginManager by lazy {
        LoginManager(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        setContentView(R.layout.activity_splash_screen) // causes error
        auth = Firebase.auth
        when {
            loginManager.isUserLoggedIn() -> {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }

            else -> {
                startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
                finish()
            }
        }
    }
}
