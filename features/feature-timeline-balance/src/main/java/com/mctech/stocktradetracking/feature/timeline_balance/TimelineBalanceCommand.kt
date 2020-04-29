package com.mctech.stocktradetracking.feature.timeline_balance

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class TimelineBalanceCommand : ViewCommand {
    sealed class Back : TimelineBalanceCommand(){
        object FromCreatePosition : Back()
        object FromEdit : Back()
    }
}