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

class MainActivity : AppCompatActivity() {
    var isHome = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!isHome) {
            return super.onOptionsItemSelected(item)
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.action_save, R.string.action_save)
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}