package com.dokari4.personalfinance.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.ui.accounts.AccountsFragment
import com.dokari4.personalfinance.databinding.ActivityMainBinding
import com.dokari4.personalfinance.ui.home.HomeFragment
import com.dokari4.personalfinance.ui.overview.OverviewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            setCurrentFragment(HomeFragment(), "Home")
        }

        setContentView(binding.root)
//
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController

//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.homeFragment, R.id.accountsFragment, R.id.overviewFragment),
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navController.navigate(R.id.homeFragment)
        setSupportActionBar(binding.layoutMain.toolbar)

        binding.layoutMain.bottomNavbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navbar_home -> setCurrentFragment(HomeFragment(), "Home")
                R.id.navbar_accounts -> setCurrentFragment(AccountsFragment(), "Accounts")
                R.id.navbar_overview -> setCurrentFragment(OverviewFragment(), "Overview")
            }
            return@setOnItemSelectedListener true
        }
    }



    private fun setCurrentFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
        supportActionBar?.title = title
    }

    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var title = "Home"
        when (item.itemId) {
            R.id.navbar_home -> {
                fragment = HomeFragment()
            }

            R.id.navbar_accounts -> {
                fragment = AccountsFragment()
                title = "Accounts"
            }

            R.id.navbar_overview -> {
                fragment = OverviewFragment()
                title = "Overview"
            }
        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                .commit()
        }
        supportActionBar?.title = title
        return true

    }*/

}