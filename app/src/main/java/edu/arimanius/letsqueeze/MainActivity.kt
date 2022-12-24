package edu.arimanius.letsqueeze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.data.entity.Theme
import edu.arimanius.letsqueeze.data.repository.UserRepository
import edu.arimanius.letsqueeze.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var isHome = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        val navController = navHostFragment.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val appBarConfiguration = AppBarConfiguration(
            navController.graph, drawerLayout,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.action_save, R.string.action_save)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupActionBarWithNavController(navController, appBarConfiguration)

        val setting = LetsQueezeDatabase.getInstance(application).settingDao().get()
        setting.observe(this) {
            it ?: return@observe
            AppCompatDelegate.setDefaultNightMode(
                when (it.theme) {
                    Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
        }

        val loggedInUser = LetsQueezeDatabase.getInstance(application).userDao().getLoggedInUser()
        loggedInUser.observe(this) {
            binding.navView.menu.clear()
            if (it == null) {
                binding.navView.inflateMenu(R.menu.initial_menu)
            } else {
                binding.navView.inflateMenu(R.menu.logged_in_menu)
            }
        }

        binding.navView.setNavigationItemSelectedListener {
            val userRepository = UserRepository(
                LetsQueezeDatabase.getInstance(application).appPropertyDao(),
                LetsQueezeDatabase.getInstance(application).userDao()
            )
            when (it.itemId) {
                R.id.nav_profile -> navController.navigate(R.id.action_homeFragment_to_profileFragment)
                R.id.nav_logout -> {
                    CoroutineScope(Dispatchers.IO).launch { userRepository.logout() }
                }
                R.id.nav_settings -> navController.navigate(R.id.action_homeFragment_to_settingFragment)
                R.id.nav_login -> navController.navigate(R.id.action_homeFragment_to_loginFragment)
                R.id.nav_register -> navController.navigate(R.id.action_homeFragment_to_registerFragment)
                R.id.nav_scoreboard -> navController.navigate(R.id.action_homeFragment_to_scoreboardFragment)
                else -> return@setNavigationItemSelectedListener false
            }
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.homeFragment)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!isHome) {
            return super.onOptionsItemSelected(item)
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}