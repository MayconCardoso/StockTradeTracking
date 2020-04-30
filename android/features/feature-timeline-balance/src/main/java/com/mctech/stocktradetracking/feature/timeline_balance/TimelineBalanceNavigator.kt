package com.mctech.stocktradetracking.feature.timeline_balance

import android.os.Bundle
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance

interface TimelineBalanceNavigator {
    fun fromTimelineToOpenPeriod()
    fun fromTimelineToEditPeriod(currentPeriod: TimelineBalance)
    fun navigateBack()
}

fun timelinePeriodFromBundle(bundle: Bundle): TimelineBalance {
    if (bundle.containsKey("currentPeriod")) {
        return bundle.getSerializable("currentPeriod") as TimelineBalance
    }
    throw IllegalArgumentException("Required argument \"currentPeriod\" is missing and does not have an android:defaultValue")
}