package com.mctech.stocktradetracking.feature.timeline_balance.list_period

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
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.GetCurrentPeriodBalanceCase
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.GetPeriodTransactionsCase

class TimelineBalanceListViewModel constructor(
    private val getCurrentPeriodBalanceCase	: GetCurrentPeriodBalanceCase,
    private val getPeriodTransactionsCase	: GetPeriodTransactionsCase
) : BaseViewModel() {

    private val currentListOfPeriods    = mutableListOf<TimelineBalance>()

    private val _periodList: MutableLiveData<ComponentState<List<TimelineBalance>>> = MutableLiveData(ComponentState.Initializing)
    val periodList: LiveData<ComponentState<List<TimelineBalance>>> = _periodList

    private val _finalBalance: MutableLiveData<ComponentState<TimelineBalance?>> = MutableLiveData(ComponentState.Initializing)
    val finalBalance: LiveData<ComponentState<TimelineBalance?>> = _finalBalance

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when (interaction) {
            is TimelineBalanceListInteraction.LoadTimelineComponent -> loadTimelineComponentInteraction()
        }
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

        _finalBalance.changeToSuccessState(result.firstOrNull())
    }
}
