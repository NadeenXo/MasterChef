package com.example.masterchef.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.fragment.NavHostFragment
import com.example.masterchef.R
import com.example.masterchef.databinding.ActivityAuthBinding
import com.example.masterchef.onboarding.OnBoardingActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Use findNavController with the NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_auth) as NavHostFragment
        val navController = navHostFragment.navController

        // Retrieve the initialFragment extra
        val initialFragmentId =
            intent.getIntExtra(OnBoardingActivity.DESTINATION_FRAGMENT, R.id.loginFragment)

        // Set the startDestination of the nav graph based on the initialFragmentId
        val navGraph = navController.navInflater.inflate(R.navigation.navigation_auth)
        navGraph.setStartDestination(initialFragmentId)
        navController.graph = navGraph

        onBackPressedDispatcher.addCallback(this@AuthActivity) {
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}