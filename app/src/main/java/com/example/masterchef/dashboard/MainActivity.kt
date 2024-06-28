package  com.example.masterchef.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.masterchef.R
import com.example.masterchef.dashboard.add.AddFragment
import com.example.masterchef.dashboard.calender.CalenderFragment
import com.example.masterchef.dashboard.home.HomeFragment
import com.example.masterchef.dashboard.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.bottom_nav)

        // Initialize with HomeFragment if savedInstanceState is null
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Set listener for BottomNavigationView
        navView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.homeFragment -> HomeFragment()
                R.id.searchFragment -> SearchFragment()
                R.id.addFragment -> AddFragment()
                R.id.calenderFragment -> CalenderFragment()
                else -> HomeFragment() // Default to HomeFragment
            }
            replaceFragment(fragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, fragment)
            .commit()
    }
}
