package com.mctech.stocktradetracking.navigation

import androidx.navigation.NavController
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator

object AppNavigatorHandler : StockShareNavigator {
    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }
}