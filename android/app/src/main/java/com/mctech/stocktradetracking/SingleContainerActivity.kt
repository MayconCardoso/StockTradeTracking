package com.mctech.stocktradetracking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mctech.stocktradetracking.navigation.AppNavigatorHandler
import kotlinx.android.synthetic.main.activity_container.*

class SingleContainerActivity : AppCompatActivity() {

    private var currentGraph : Int? = R.navigation.stock_share_navigation_graph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        setUpBottomNavigation()

        currentGraph = savedInstanceState?.getInt(
            "currentNavigation", R.navigation.stock_share_navigation_graph
        )

        startNavigationFlow(currentGraph)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentGraph?.run {
            outState.putInt("currentNavigation", this)
        }
    }

    override fun onStart() {
        super.onStart()
        AppNavigatorHandler.bind(getNavigationController())
    }

    override fun onStop() {
        AppNavigatorHandler.unbind()
        super.onStop()
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavigationController().navigateUp()
    }

    override fun onBackPressed() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation_main)
        if(bottomNavigation.selectedItemId != R.id.nav_stock){
            bottomNavigation.selectedItemId = R.id.nav_stock
            return
        }

        super.onBackPressed()
    }

    private fun getNavigationController() = Navigation.findNavController(this, R.id.nav_host_fragment)

    private fun setUpBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_stock-> {
                    startNavigationFlow(R.navigation.stock_share_navigation_graph)
                }
                R.id.nav_timeline -> {
                    startNavigationFlow(R.navigation.timeline_balance_navigation_graph)
                }
                R.id.nav_today_position ->{

                }
                R.id.nav_more -> {

                }
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun startNavigationFlow(startDestinationFragment: Int?) {
        currentGraph = startDestinationFragment

        val navHostFragment = nav_host_fragment as NavHostFragment

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(
            startDestinationFragment ?: R.navigation.stock_share_navigation_graph
        )

        navHostFragment.navController.graph = graph

        setupActionBarWithNavController(getNavigationController())
        AppNavigatorHandler.bind(getNavigationController())
    }
}
