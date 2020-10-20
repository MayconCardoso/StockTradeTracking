package com.mctech.stocktradetracking

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mctech.stocktradetracking.navigation.AppNavigatorHandler

class SingleContainerActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_container)

    setUpNavigation()
    setUpBottomNavigation()
    setUpActionBar()
  }

  override fun onDestroy() {
    AppNavigatorHandler.unbind()
    super.onDestroy()
  }

  override fun onSupportNavigateUp(): Boolean {
    return getNavigationController().navigateUp()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      getNavigationController().navigateUp()
      return true
    }

    return super.onOptionsItemSelected(item)
  }

  private fun getNavigationController() = Navigation.findNavController(this, R.id.nav_host_fragment)

  private fun setUpNavigation() {
    AppNavigatorHandler.bind(getNavigationController())
  }

  private fun setUpBottomNavigation() {
    val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation_main)
    setupWithNavController(bottomNav, getNavigationController())
  }

  private fun setUpActionBar() {
    setupActionBarWithNavController(getNavigationController())
  }
}
