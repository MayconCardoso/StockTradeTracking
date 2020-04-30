package com.mctech.stocktradetracking.feature.timeline_balance.list_period

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class TimelineBalanceListInteraction : UserInteraction {
    object LoadTimelineComponent : TimelineBalanceListInteraction()
}