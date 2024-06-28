package com.example.masterchef.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.masterchef.auth.LoginManager
import com.example.masterchef.dashboard.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private val loginManager: LoginManager by lazy {
        LoginManager(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        setContentView(R.layout.activity_splash_screen) // causes error

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
