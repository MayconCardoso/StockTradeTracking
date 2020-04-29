package com.mctech.stocktradetracking.feature.timeline_balance.di

import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timelineBalanceViewModel = module {
    viewModel {
        TimelineBalanceViewModel(
            createPeriodCase = get(),
            editPeriodCase = get(),
            getCurrentPeriodBalanceCase = get(),
            getPeriodTransactionsCase = get(),
            depositMoneyCase = get(),
            withdrawMoneyCase = get()
        )
    }
}