package com.mctech.stocksharetracking.feature.stock_share_filter

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stockShareFilterViewModelModule = module {
  viewModel {
    StockShareFilterViewModel(
      observeCurrentFilterCase = get(),
      saveStockShareFilterCase = get()
    )
  }
}