package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.stocktradetracking.feature.stock_share.add_position.StockShareBuyViewModel
import com.mctech.stocktradetracking.feature.stock_share.edit_position.StockShareEditPositionViewModel
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val stockShareViewModelModule = module {
  viewModel(named("stockShareViewModel")) {
    StockShareListViewModel(
      getFinalBalanceCase = get(named("stockBalance")),
      selectBestStockShareCase = get(named("stockBestSelector")),
      selectWorstStockShareCase = get(named("stockWorstSelector")),
      filterStockListCase = get(named("stockFilter")),
      observeStockListCase = get(),
      getMarketStatusCase = get(),
      observeCurrentFilterCase = get(),
      syncStockSharePriceCase = get()
    )
  }

  viewModel(named("dailyStockViewModel")) {
    StockShareListViewModel(
      getFinalBalanceCase = get(named("dailyBalance")),
      selectBestStockShareCase = get(named("dailyBestSelector")),
      selectWorstStockShareCase = get(named("dailyWorstSelector")),
      filterStockListCase = get(named("dailyStockFilter")),
      observeStockListCase = get(),
      getMarketStatusCase = get(),
      observeCurrentFilterCase = get(),
      syncStockSharePriceCase = get()
    )
  }

  viewModel(named("closedStockViewModel")) {
    StockShareListViewModel(
      observeStockListCase = get(named("closedListObserver")),
      selectWorstStockShareCase = get(named("stockWorstSelector")),
      selectBestStockShareCase = get(named("stockBestSelector")),
      getFinalBalanceCase = get(named("stockBalance")),
      filterStockListCase = get(named("stockFilter")),
      getMarketStatusCase = get(),
      observeCurrentFilterCase = get(),
      syncStockSharePriceCase = get()
    )
  }

  viewModel {
    StockShareBuyViewModel(
      saveStockShareCase = get()
    )
  }

  viewModel {
    StockShareEditPositionViewModel(
      saveStockShareCase = get(),
      editStockSharePriceCase = get(),
      deleteStockShareCase = get(),
      closeStockShareCase = get()
    )
  }
}