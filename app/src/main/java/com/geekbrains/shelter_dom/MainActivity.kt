package com.geekbrains.shelter_dom





import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.geekbrains.shelter_dom.databinding.ActivityMainBinding
import com.geekbrains.shelter_dom.ui.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //because there is no dark theme, later delete
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

    }

    override fun onResume() {
        super.onResume()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        App.INSTANCE.router.exit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigation_home -> App.INSTANCE.router.navigateTo(Screens.OpenHomeFragment())
            R.id.navigation_about -> App.INSTANCE.router.navigateTo(Screens.OpenAboutFragment())
            R.id.navigation_our_pets -> App.INSTANCE.router.navigateTo(Screens.OpenOurPetsFragment())
            R.id.navigation_help -> App.INSTANCE.router.navigateTo(Screens.OpenHelpShelterFragment())
            R.id.navigation_contacts -> TODO()

        }
        binding.drawerLayout.closeDrawers()
        return true
    }
}