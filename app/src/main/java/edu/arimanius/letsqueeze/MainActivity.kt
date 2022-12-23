package edu.arimanius.letsqueeze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.data.entity.Theme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.fragment))
        val setting = LetsQueezeDatabase.getInstance(application).settingDao().get()
        setting.observe(this) {
            AppCompatDelegate.setDefaultNightMode(
                when (it.theme) {
                    Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
        }
    }
}