package com.mctech.stocktradetracking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.mctech.stocktradetracking.navigation.AppNavigatorHandler

class SingleContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        setupActionBarWithNavController(getNavigationController())
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

    private fun getNavigationController() = Navigation.findNavController(this, R.id.nav_host_fragment)
}
