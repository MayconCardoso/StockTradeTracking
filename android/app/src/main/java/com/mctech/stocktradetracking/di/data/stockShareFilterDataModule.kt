package com.mctech.stocktradetracking.di.data

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.mctech.stocktradetracking.data.stock_share_filter.datasource.LocalStockShareFilterDataSource
import com.mctech.stocktradetracking.data.stock_share_filter.datasource.LocalStockShareFilterDataSourceImpl
import com.mctech.stocktradetracking.data.stock_share_filter.repository.StockShareFilterRepository
import com.mctech.stocktradetracking.domain.stock_share_filter.service.StockShareFilterService
import org.koin.dsl.module

val stockShareFilterDataModule = module {
    single {
        val application : Application = get()

        LocalStockShareFilterDataSourceImpl(
            sharedPreferences = application.getSharedPreferences(
                "StockFilter",
                Context.MODE_PRIVATE
            ),
            gson = Gson()
        ) as LocalStockShareFilterDataSource
    }

    single {
        StockShareFilterRepository(
            dataSource = get()
        ) as StockShareFilterService
    }
}
