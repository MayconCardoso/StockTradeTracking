package com.mctech.stocktradetracking.feature.timeline_balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToErrorState
import com.mctech.architecture.mvvm.x.core.ktx.changeToLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.*

class TimelineBalanceViewModel constructor(
    private val getCurrentPeriodBalanceCase	: GetCurrentPeriodBalanceCase,
    private val getPeriodTransactionsCase	: GetPeriodTransactionsCase,

    private val createPeriodCase			: CreatePeriodCase,
    private val editPeriodCase				: EditPeriodCase,

    private val depositMoneyCase			: DepositMoneyCase,
    private val withdrawMoneyCase			: WithdrawMoneyCase
) : BaseViewModel() {

    private val currentListOfPeriods = mutableListOf<TimelineBalance>()

    private val _periodList: MutableLiveData<ComponentState<List<TimelineBalance>>> = MutableLiveData(ComponentState.Initializing)
    val periodList: LiveData<ComponentState<List<TimelineBalance>>> = _periodList

    private val _finalBalance: MutableLiveData<ComponentState<TimelineBalance>> = MutableLiveData(ComponentState.Initializing)
    val finalBalance: LiveData<ComponentState<TimelineBalance>> = _finalBalance

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when (interaction) {
            is TimelineBalanceInteraction.LoadTimelineComponent -> loadTimelineComponentInteraction()
            is TimelineBalanceInteraction.CreatePeriod 			-> createPeriodInteraction(
                interaction.period,
                interaction.monthInvestment,
                interaction.monthProfit
            )
        }
    }

    private suspend fun createPeriodInteraction(periodTag: String, monthInvestment : Double, monthProfit : Double) {
        val period = TimelineBalance(
            periodTag = periodTag,
            parentPeriodTag = currentListOfPeriods.firstOrNull()?.periodTag,
            periodInvestment = monthInvestment,
            periodProfit = monthProfit
        )

        // Save new position
        createPeriodCase.execute(period)

        // Update screen
        loadTimelineComponentInteraction()

        // Return to list
        sendCommand(TimelineBalanceCommand.Back.FromCreatePosition)
    }

    private suspend fun loadTimelineComponentInteraction() {
        _periodList.changeToLoadingState()
        _finalBalance.changeToLoadingState()

        when (val result = getCurrentPeriodBalanceCase.execute()) {
            is Result.Success -> {
                computeFinalBalance(result.result)
                _periodList.changeToSuccessState(result.result)
            }
            is Result.Failure -> {
                _periodList.changeToErrorState(result.throwable)
            }
        }
    }

    private fun computeFinalBalance(result: List<TimelineBalance>) {
        currentListOfPeriods.clear()
        currentListOfPeriods.addAll(result)

        _finalBalance.changeToSuccessState(result.first())
    }
}
