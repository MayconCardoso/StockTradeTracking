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

    private val currentListOfPeriods    = mutableListOf<TimelineBalance>()
    private var currentPeriod 		    : TimelineBalance? = null

    private val _periodList: MutableLiveData<ComponentState<List<TimelineBalance>>> = MutableLiveData(ComponentState.Initializing)
    val periodList: LiveData<ComponentState<List<TimelineBalance>>> = _periodList

    private val _finalBalance: MutableLiveData<ComponentState<TimelineBalance?>> = MutableLiveData(ComponentState.Initializing)
    val finalBalance: LiveData<ComponentState<TimelineBalance?>> = _finalBalance

    private val _currentPeriodState : MutableLiveData<TimelineBalance> = MutableLiveData()
    val currentPeriodState : LiveData<TimelineBalance> = _currentPeriodState

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when (interaction) {
            is TimelineBalanceInteraction.LoadTimelineComponent -> loadTimelineComponentInteraction()
            is TimelineBalanceInteraction.OpenPeriodDetails     -> openPeriodDetailsInteraction(
                interaction.period
            )
            is TimelineBalanceInteraction.CreatePeriod 			-> createPeriodInteraction(
                interaction.period,
                interaction.monthInvestment,
                interaction.monthProfit
            )
            is TimelineBalanceInteraction.EditPeriod 			-> editPeriodInteraction(
                interaction.period,
                interaction.monthInvestment,
                interaction.monthProfit,
                interaction.finalBalance
            )
        }
    }

    private suspend fun createPeriodInteraction(periodTag: String, monthInvestment : Double, monthProfit : Double) {
        val period = TimelineBalance(
            periodTag = periodTag,
            periodInvestment = monthInvestment,
            periodProfit = monthProfit
        )

        // Save new position
        createPeriodCase.execute(period, currentListOfPeriods.firstOrNull())

        // Update screen
        loadTimelineComponentInteraction()

        // Return to list
        sendCommand(TimelineBalanceCommand.Back.FromCreatePosition)
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


        // Update screen
        loadTimelineComponentInteraction()

        // Return to list
        sendCommand(TimelineBalanceCommand.Back.FromEditPosition)
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

    private fun openPeriodDetailsInteraction(period: TimelineBalance) {
        currentPeriod = period
        _currentPeriodState.value = period
    }

    private fun computeFinalBalance(result: List<TimelineBalance>) {
        currentListOfPeriods.clear()
        currentListOfPeriods.addAll(result)

        _finalBalance.changeToSuccessState(result.firstOrNull())
    }
}
