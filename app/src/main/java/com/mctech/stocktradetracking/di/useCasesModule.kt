package com.mctech.stocktradetracking.di

import com.mctech.stocktradetracking.domain.stock_share.interaction.BuyStockShareCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.EditStockShareValueCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.GetStockShareListCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.SellStockShareCase
import org.koin.dsl.module

val useCasesModule = module {
    // Stock Share
    single { BuyStockShareCase(service = get(), logger = get()) }
    single { EditStockShareValueCase(service = get(), logger = get()) }
    single { GetStockShareListCase(service = get(), logger = get()) }
    single { SellStockShareCase(service = get(), logger = get()) }
}