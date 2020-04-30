package com.mctech.stocktradetracking.feature.timeline_balance

import com.mctech.stocktradetracking.feature.timeline_balance.add_period.TimelineBalanceAddViewModel
import com.mctech.stocktradetracking.feature.timeline_balance.edit_period.TimelineBalanceEditViewModel
import com.mctech.stocktradetracking.feature.timeline_balance.list_period.TimelineBalanceListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timelineBalanceViewModel = module {
    viewModel {
        TimelineBalanceListViewModel(
            getCurrentPeriodBalanceCase = get(),
            getPeriodTransactionsCase = get()
        )
    }

    viewModel {
        TimelineBalanceAddViewModel(
            createPeriodCase = get()
        )
    }

    viewModel {
        TimelineBalanceEditViewModel(
            editPeriodCase = get()
        )
    }
}