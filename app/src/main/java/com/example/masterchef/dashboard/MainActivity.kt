package  com.example.masterchef.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.masterchef.R
import com.example.masterchef.dashboard.country.CountryFragment
import com.example.masterchef.dashboard.favorite.FavouriteFragment
import com.example.masterchef.dashboard.home.HomeFragment
import com.example.masterchef.dashboard.plan.PlanFragment
import com.example.masterchef.dashboard.search.SearchFragment
import com.example.masterchef.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var logout: Logout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize with HomeFragment if savedInstanceState is null
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Set listener for BottomNavigationView
        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.country -> CountryFragment()
                R.id.fav -> FavouriteFragment()
                R.id.plan -> PlanFragment()
//                R.id.add -> AddFragment()
//                R.id.calender -> CalenderFragment()
                R.id.search -> SearchFragment()
                else -> HomeFragment()
            }
            replaceFragment(fragment)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                Toast.makeText(this, "settings", Toast.LENGTH_LONG).show()
                return true
            }

            R.id.share -> {
                return true
            }

            R.id.logout -> {
                logout.onClick()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, fragment)
            .commit()
    }
}

interface Logout {
    fun onClick()
}
