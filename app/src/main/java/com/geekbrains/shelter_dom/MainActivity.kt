package com.geekbrains.shelter_dom


import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.geekbrains.shelter_dom.databinding.ActivityMainBinding
import com.geekbrains.shelter_dom.ui.MainView
import com.geekbrains.shelter_dom.ui.Screens
import com.geekbrains.shelter_dom.utils.App
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), MainView,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true) // show back button
                binding.toolbar.setNavigationOnClickListener { onBackPressed() }
            } else {
                //show hamburger
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                toggle.syncState()
                binding.toolbar.setNavigationOnClickListener {
                    binding.drawerLayout.openDrawer(
                        GravityCompat.START
                    )
                }
            }
        }

        binding.registerTextView?.setOnClickListener {
            App.INSTANCE.router.navigateTo(Screens.OpenAuthFragment())
            binding.drawerLayout.closeDrawers()
        }

        binding.ivOpenUser?.setOnClickListener {
            App.INSTANCE.router.navigateTo(Screens.OpenUserFragment())
            binding.drawerLayout.closeDrawers()
        }

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
        when (item.itemId) {
            R.id.navigation_home -> App.INSTANCE.router.replaceScreen(Screens.OpenHomeFragment())
            R.id.navigation_about -> App.INSTANCE.router.replaceScreen(Screens.OpenAboutFragment())
            R.id.navigation_our_pets -> App.INSTANCE.router.replaceScreen(Screens.OpenOurPetsFragment())
            R.id.navigation_help -> App.INSTANCE.router.replaceScreen(Screens.OpenHelpShelterFragment())
            R.id.navigation_contacts -> App.INSTANCE.router.replaceScreen(Screens.OpenContactsFragment())

        }
        binding.drawerLayout.closeDrawers()
        return true
    }
}