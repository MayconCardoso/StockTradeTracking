package com.mctech.stocktradetracking.feature.timeline_balance.edit_period

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.EditPeriodCase

class TimelineBalanceEditViewModel constructor(
    private val editPeriodCase  : EditPeriodCase
) : BaseViewModel() {

    private var currentPeriod : TimelineBalance? = null

    private val _currentPeriodState : MutableLiveData<ComponentState<TimelineBalance>> = MutableLiveData(ComponentState.Initializing)
    val currentPeriodState : LiveData<ComponentState<TimelineBalance>> = _currentPeriodState

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when (interaction) {
            is TimelineBalanceEditInteraction.OpenPeriodDetails     -> openPeriodDetailsInteraction(
                interaction.period
            )
            is TimelineBalanceEditInteraction.EditPeriod 			-> editPeriodInteraction(
                interaction.period,
                interaction.monthInvestment,
                interaction.monthProfit,
                interaction.finalBalance
            )
        }
    }

    private suspend fun editPeriodInteraction(
        tag: String,
        monthInvestment : Double,
        monthProfit : Double,
        finalBalance : Double
    ) {
        val updatedPeriod = TimelineBalance(
            periodTag = tag,
            periodInvestment = monthInvestment,
            periodProfit = monthProfit
        )

        // Update value.
        currentPeriod?.run {
            editPeriodCase.execute(this, updatedPeriod, finalBalance)
        }

        // Return to list
        sendCommand(TimelineBalanceEditCommand.NavigationBack)
    }

    private fun openPeriodDetailsInteraction(period: TimelineBalance) {
        currentPeriod = period
        _currentPeriodState.changeToSuccessState(period)
    }
}
