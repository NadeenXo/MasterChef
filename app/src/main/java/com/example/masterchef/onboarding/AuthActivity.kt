package com.example.masterchef.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.masterchef.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
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
    }
}