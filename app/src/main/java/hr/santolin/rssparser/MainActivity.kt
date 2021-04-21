package hr.santolin.rssparser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.santolin.rssparser.databinding.ActivityMainBinding
import hr.santolin.rssparser.utils.isOnline
import hr.santolin.rssparser.utils.showToast


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) //obavezno prvo inflejtam layout i bindam i onda sve ostalo

        initHamburgerMenu()
        initNavigation()

        if (isOnline()) {
          Intent(this, NewsService::class.java).apply {
                NewsService.enqueueWork(this@MainActivity, this)
            }
        } else {
            showToast("Connect to the internet!")
        }

    }
    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // <-
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)//exit menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun initNavigation() {//bez ovoga se prikazuje ali ne radi
        var navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toggleDrawerMenu()
                return true
            }
            R.id.miExit -> {
                exitApp()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
        //return true
    }

    private fun toggleDrawerMenu() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun exitApp() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_baseline_exit_to_app_24)
            .setTitle("Exit app")
            .setMessage("Really?")
            .setCancelable(true)
            .setPositiveButton("Go out") { _, _ -> finish() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}