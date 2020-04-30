package com.mctech.stocktradetracking.navigation

import androidx.navigation.NavController
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListFragmentDirections
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceNavigator
import com.mctech.stocktradetracking.feature.timeline_balance.list_period.TimelineBalanceListFragmentDirections

object AppNavigatorHandler : StockShareNavigator, TimelineBalanceNavigator {
    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }

    override fun navigateBack() {
        navController?.navigateUp()
    }

    override fun fromStockShareToEditPosition(stockShare: StockShare) {
        val destination = StockShareListFragmentDirections.actionStockShareListFragmentToStockShareEditPriceFragment(
            stockShare
        )
        navController?.navigate(destination)
    }

    override fun fromStockShareToBuyPosition() {
        val destination = StockShareListFragmentDirections.actionStockShareListFragmentToStockShareBuyFragment()
        navController?.navigate(destination)
    }

    override fun fromTimelineToEditPeriod(currentPeriod: TimelineBalance) {
        val destination = TimelineBalanceListFragmentDirections.actionTimelineBalanceFragmentToTimelineBalanceEditPeriodFragment(
            currentPeriod
        )
        navController?.navigate(destination)
    }

    override fun fromTimelineToOpenPeriod() {
        val destination = TimelineBalanceListFragmentDirections.actionTimelineBalanceFragmentToTimelineBalanceAddPeriodFragment()
        navController?.navigate(destination)
    }
}