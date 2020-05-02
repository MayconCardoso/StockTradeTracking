package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.stocktradetracking.feature.stock_share.add_position.StockShareBuyViewModel
import com.mctech.stocktradetracking.feature.stock_share.daily_variation.StockDailyVariationListViewModel
import com.mctech.stocktradetracking.feature.stock_share.edit_position.StockShareEditPositionViewModel
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val stockShareViewModelModule = module {
    viewModel {
        StockShareListViewModel(
            observeStockListCase = get(),
            getMarketStatusCase = get(),
            getFinalBalanceCase = get(named("stockBalance")),
            selectBestStockShareCase = get(),
            selectWorstStockShareCase = get(),
            groupStockShareListCase = get(),
            syncStockSharePriceCase = get()
        )
    }

    viewModel {
        StockDailyVariationListViewModel(
            observeStockListCase = get(),
            getMarketStatusCase = get(),
            getFinalBalanceCase = get(named("dailyBalance")),
            selectBestStockShareCase = get(),
            selectWorstStockShareCase = get(),
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
            editStockSharePriceCase = get(),
            deleteStockShareCase = get()
        )
    }
}