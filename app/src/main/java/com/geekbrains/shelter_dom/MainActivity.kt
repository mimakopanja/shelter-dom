package com.geekbrains.shelter_dom


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.geekbrains.shelter_dom.databinding.ActivityMainBinding
import com.geekbrains.shelter_dom.ui.MainView
import com.geekbrains.shelter_dom.ui.Screens
import com.geekbrains.shelter_dom.utils.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationView
import com.shashank.sony.fancytoastlib.FancyToast


class MainActivity : AppCompatActivity(), MainView,
    NavigationView.OnNavigationItemSelectedListener {

    private val userSharedPref = SharedPrefManager.getInstance().user
    private lateinit var token: String

    private lateinit var binding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toggle = initActionBar()
        initSharedPref()
        openRegisterScreen()

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

//        Logout User
        binding.logoutButton?.onCLick {
            SharedPrefManager.getInstance().clear()
            binding.registerTextView?.let { it1 -> setVisibility(it1, true) }
            setVisibility(binding.logoutButton!!, false)
            binding.ivOpenUser?.let { it1 -> setVisibility(it1, false) }
            binding.drawerLayout.closeDrawers()
            customToast(applicationContext, getString(R.string.login_successful), FancyToast.INFO)
        }

        binding.ivOpenUser?.onCLick {
            App.INSTANCE.router.replaceScreen(Screens.OpenUserInfoFragment(userSharedPref))
            binding.drawerLayout.closeDrawers()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
                binding.toolbar.setNavigationOnClickListener { onBackPressed() }
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false) //show hamburger
                toggle.syncState()
                binding.toolbar.setNavigationOnClickListener {
                    binding.drawerLayout.openDrawer(
                        GravityCompat.START
                    )
                }
            }
        }
    }

    private fun openRegisterScreen() {
        binding.registerTextView?.onCLick {
            App.INSTANCE.router.navigateTo(Screens.OpenAuthFragment())
            binding.drawerLayout.closeDrawers()
        }
    }

    private fun initActionBar(): ActionBarDrawerToggle {
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        return toggle
    }

    private fun initSharedPref() {
        token = SharedPrefManager.getInstance().token.toString()
        if (userSharedPref.isLoggedIn == true) {
            binding.registerTextView?.let { setVisibility(it, false) }
            binding.ivOpenUser?.let { setVisibility(it, true) }
            binding.logoutButton?.let { setVisibility(it, true) }
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
            R.id.navigation_fav -> App.INSTANCE.router.replaceScreen(Screens.OpenFavFragment(token))
        }
        binding.drawerLayout.closeDrawers()
        return true
    }
}