package com.mctech.stocktradetracking.feature.timeline_balance.edit_period

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class TimelineBalanceEditCommand : ViewCommand {
    object NavigationBack : TimelineBalanceEditCommand()
}