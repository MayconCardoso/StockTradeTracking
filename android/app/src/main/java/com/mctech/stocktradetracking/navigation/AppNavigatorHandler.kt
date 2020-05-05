package com.mctech.stocktradetracking.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.mctech.stocktradetracking.R
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceNavigator
import com.mctech.stocktradetracking.feature.timeline_balance.list_period.TimelineBalanceListFragmentDirections

object AppNavigatorHandler :
    StockShareNavigator,
    TimelineBalanceNavigator
{
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

    override fun fromStockListToEditPosition(stockShare: StockShare) {
        val bundle = Bundle().apply {
            putSerializable("stockShare", stockShare)
        }
        navController?.navigate(
            R.id.action_global_stockShareEditPriceFragment,
            bundle
        )
    }

    override fun fromStockListToBuyPosition() {
        navController?.navigate(R.id.action_global_stockShareBuyFragment)
    }

    override fun fromStockListToFilter() {

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