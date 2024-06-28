package com.example.masterchef.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.view.WindowCompat
import com.example.masterchef.R
import com.example.masterchef.auth.AuthActivity
import com.example.masterchef.auth.LoginManager

class OnBoardingActivity : AppCompatActivity() {
    companion object Constants {
        const val DESTINATION_FRAGMENT = "initialFragment"
    }

    lateinit var login: TextView
    lateinit var skip: TextView
    lateinit var signup: Button
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        login = findViewById(R.id.tv_login)
        skip = findViewById(R.id.tv_skip)
        signup = findViewById(R.id.btn_signup_onboarding)

        //to make the edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        sharedPref =
            this.getSharedPreferences(LoginManager.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

        login.setOnClickListener {
            startLoginFragment()
        }

        signup.setOnClickListener {
            startSignupFragment()
        }
        skip.setOnClickListener {
            sharedPref.edit {
                putBoolean(LoginManager.PREFERENCE_IS_LOGGED, false)
            }
        }
    }

    private fun startSignupFragment() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(DESTINATION_FRAGMENT, R.id.signupFragment)
        startActivity(intent)
    }

    private fun startLoginFragment() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(DESTINATION_FRAGMENT, R.id.loginFragment)
        startActivity(intent)
    }

    //todo: check this
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}