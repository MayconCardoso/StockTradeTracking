package com.mctech.stocktradetracking.di

import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import org.koin.dsl.module

val useCasesModule = module {
    // Stock Share
    single { SaveStockShareCase(service = get(), logger = get()) }
    single { EditStockShareValueCase(service = get(), logger = get()) }
    single { GetStockShareListCase(service = get(), logger = get()) }
    single { SellStockShareCase(service = get(), logger = get()) }
    single { GetFinalBalanceCase() }
    single { GroupStockShareListCase() }
    single { GetWorstStockShareCase(groupStockShareListCase = get()) }
    single { GetBestStockShareCase(groupStockShareListCase = get()) }
}