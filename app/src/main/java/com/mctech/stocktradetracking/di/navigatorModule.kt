package com.mctech.stocktradetracking.di

import com.mctech.stocksharetracking.feature.stock_share_filter.StockShareFilterNavigator
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceNavigator
import com.mctech.stocktradetracking.navigation.AppNavigatorHandler
import org.koin.dsl.module

val navigatorModule = module {

  single<StockShareNavigator> {
    AppNavigatorHandler
  }

  single<TimelineBalanceNavigator> {
    AppNavigatorHandler
  }

  single<StockShareFilterNavigator> {
    AppNavigatorHandler
  }
}