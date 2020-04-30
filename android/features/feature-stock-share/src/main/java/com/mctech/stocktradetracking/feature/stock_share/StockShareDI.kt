package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.stocktradetracking.feature.stock_share.add_position.StockShareBuyViewModel
import com.mctech.stocktradetracking.feature.stock_share.edit_position.StockShareEditPositionViewModel
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stockShareViewModelModule = module {
    viewModel {
        StockShareListViewModel(
            observeStockListCase = get(),
            getFinalBalanceCase = get(),
            getWorstStockShareCase = get(),
            getBestStockShareCase = get(),
            groupStockShareListCase = get(),
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
            editStockShareValueCase = get(),
            deleteStockShareCase = get()
        )
    }
}