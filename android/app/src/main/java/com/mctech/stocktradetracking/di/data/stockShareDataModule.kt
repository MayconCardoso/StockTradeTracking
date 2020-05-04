package com.mctech.stocktradetracking.di.data

import com.mctech.stocktradetracking.data.stock_share.datasource.LocalStockShareDataSource
import com.mctech.stocktradetracking.data.stock_share.datasource.LocalStockShareDataSourceImpl
import com.mctech.stocktradetracking.data.stock_share.datasource.RemoteStockShareDataSource
import com.mctech.stocktradetracking.data.stock_share.datasource.RemoteStockShareDataSourceImpl
import com.mctech.stocktradetracking.data.stock_share.repository.StockShareRepository
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import org.koin.dsl.module

val stockShareDataModule = module {
    single {
        LocalStockShareDataSourceImpl(
            stockShareDao = get()
        ) as LocalStockShareDataSource
    }

    single {
        RemoteStockShareDataSourceImpl(
            api = get()
        ) as RemoteStockShareDataSource
    }

    single {
        StockShareRepository(
            logger = get(),
            localDataSource = get(),
            remoteDataSource = get()
        ) as StockShareService
    }
}
