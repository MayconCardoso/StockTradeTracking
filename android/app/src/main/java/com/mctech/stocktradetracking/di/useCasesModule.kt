package com.mctech.stocktradetracking.di

import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.*
import org.koin.core.qualifier.named
import org.koin.dsl.module


val useCasesModule = module {
    // Stock Share
    single { SaveStockShareCase(service = get(), logger = get()) }
    single { DeleteStockShareCase(service = get(), logger = get()) }
    single { EditStockSharePriceCase(service = get(), logger = get()) }
    single { SellStockShareCase(service = get(), logger = get()) }
    single { SyncStockSharePriceCase(service = get(), logger = get()) }
    single { GetStockShareListCase(service = get(), logger = get()) }
    single { ObserveStockShareListCase(service = get()) }
    single { GetFinalBalanceCase() }
    single { GroupStockShareListCase() }
    single { SelectWorstStockShareCase(groupStockShareListCase = get()) }
    single { SelectBestStockShareCase(groupStockShareListCase = get()) }
    single(named("stockBalance")) { GetFinalBalanceCase() as ComputeBalanceStrategy }
    single(named("dailyBalance")) { GetFinalDailyBalanceCase() as ComputeBalanceStrategy }

    // Timeline balance
    single { CreatePeriodCase(service = get(), logger = get()) }
    single { EditPeriodCase(service = get(), logger = get()) }
    single { GetCurrentPeriodBalanceCase(service = get(), logger = get()) }
    single { GetPeriodTransactionsCase(service = get()) }
    single { DepositMoneyCase(service = get()) }
    single { WithdrawMoneyCase(service = get()) }
}