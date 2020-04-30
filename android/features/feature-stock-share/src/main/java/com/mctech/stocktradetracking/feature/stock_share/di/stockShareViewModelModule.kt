package com.mctech.stocktradetracking.feature.stock_share.di

import com.mctech.stocktradetracking.feature.stock_share.StockShareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stockShareViewModelModule = module {
    viewModel {
        StockShareViewModel(
            getStockShareListCase       = get(),
            getFinalBalanceCase         = get(),
            getWorstStockShareCase      = get(),
            getBestStockShareCase       = get(),
            groupStockShareListCase     = get(),

            saveStockShareCase          = get(),
            sellStockShareCase          = get(),
            editStockShareValueCase     = get(),
            deleteStockShareCase        = get(),
            syncStockSharePriceCase     = get()
        )
    }
}