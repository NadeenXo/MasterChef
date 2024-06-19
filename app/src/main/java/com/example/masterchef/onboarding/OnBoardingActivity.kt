package com.example.masterchef.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.masterchef.R

class OnBoardingActivity : AppCompatActivity() {
    companion object Constants {
        const val DESTINATION_FRAGMENT = "initialFragment"
    }

    lateinit var login: TextView
    lateinit var signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        login = findViewById(R.id.tv_login)
        login.setOnClickListener {
            startLoginFragment()
        }

        signup.setOnClickListener {
            startSignupFragment()
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}