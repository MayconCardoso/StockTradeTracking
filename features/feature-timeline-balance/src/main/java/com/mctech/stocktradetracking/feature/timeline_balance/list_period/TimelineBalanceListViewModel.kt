package com.mctech.stocktradetracking.feature.timeline_balance.list_period

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.OnInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToErrorState
import com.mctech.architecture.mvvm.x.core.ktx.changeToLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.GetCurrentPeriodBalanceCase

class TimelineBalanceListViewModel constructor(
  private val getCurrentPeriodBalanceCase: GetCurrentPeriodBalanceCase
) : BaseViewModel() {

  private val currentListOfPeriods = mutableListOf<TimelineBalance>()

  private val _periodList: MutableLiveData<ComponentState<List<TimelineBalance>>> = MutableLiveData(ComponentState.Initializing)
  val periodList: LiveData<ComponentState<List<TimelineBalance>>> = _periodList

  private val _balanceChartList: MutableLiveData<ComponentState<List<TimelineBalance>>> = MutableLiveData(ComponentState.Initializing)
  val balanceChartList: LiveData<ComponentState<List<TimelineBalance>>> = _balanceChartList

  private val _finalBalance: MutableLiveData<ComponentState<TimelineBalance?>> = MutableLiveData(ComponentState.Initializing)
  val finalBalance: LiveData<ComponentState<TimelineBalance?>> = _finalBalance

  @OnInteraction(TimelineBalanceListInteraction.LoadTimelineComponent::class)
  private suspend fun loadTimelineComponentInteraction() {
    _periodList.changeToLoadingState()
    _balanceChartList.changeToLoadingState()
    _finalBalance.changeToLoadingState()

    when (val result = getCurrentPeriodBalanceCase.execute()) {
      is Result.Success -> {
        computeFinalBalance(result.result)
        _periodList.changeToSuccessState(result.result)
        _balanceChartList.changeToSuccessState(result.result.reversed())
      }
      is Result.Failure -> {
        _balanceChartList.changeToErrorState(result.throwable)
        _periodList.changeToErrorState(result.throwable)
        _finalBalance.changeToErrorState(result.throwable)
      }
    }
  }

  private fun computeFinalBalance(result: List<TimelineBalance>) {
    currentListOfPeriods.clear()
    currentListOfPeriods.addAll(result)

    _finalBalance.changeToSuccessState(result.firstOrNull())
  }
}
