package com.mctech.stocktradetracking.data.stock_share.di

import com.mctech.stocktradetracking.data.stock_share.datasource.LocalStockShareDataSource
import com.mctech.stocktradetracking.data.stock_share.datasource.StockShareDataSource
import com.mctech.stocktradetracking.data.stock_share.repository.StockShareRepository
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import org.koin.dsl.module

val stockShareDataModule = module {
    single {
        LocalStockShareDataSource(
            stockShareDao = get()
        ) as StockShareDataSource
    }

    single {
        StockShareRepository(
            localDataSource = get()
        ) as StockShareService
    }
}
