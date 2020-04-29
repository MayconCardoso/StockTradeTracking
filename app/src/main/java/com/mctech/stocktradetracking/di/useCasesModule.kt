package com.mctech.stocktradetracking.di

import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.*
import org.koin.dsl.module

val useCasesModule = module {
    // Stock Share
    single { SaveStockShareCase(service = get(), logger = get()) }
    single { DeleteStockShareCase(service = get(), logger = get()) }
    single { EditStockShareValueCase(service = get(), logger = get()) }
    single { GetStockShareListCase(service = get(), logger = get()) }
    single { SellStockShareCase(service = get(), logger = get()) }
    single { GetFinalBalanceCase() }
    single { GroupStockShareListCase() }
    single { GetWorstStockShareCase(groupStockShareListCase = get()) }
    single { GetBestStockShareCase(groupStockShareListCase = get()) }

    // Timeline
    single { CreatePeriodCase(service = get()) }
    single { EditPeriodCase(service = get()) }
    single { GetListOfPeriodsBalanceCase(service = get()) }
    single { GetPeriodTransactionsCase(service = get()) }
    single { DepositMoneyCase(service = get()) }
    single { WithdrawMoneyCase(service = get()) }
}