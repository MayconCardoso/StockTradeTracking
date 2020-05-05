package com.mctech.stocktradetracking.feature.timeline_balance.add_period

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class TimelineBalanceAddCommand : ViewCommand {
    object NavigationBack : TimelineBalanceAddCommand()
}