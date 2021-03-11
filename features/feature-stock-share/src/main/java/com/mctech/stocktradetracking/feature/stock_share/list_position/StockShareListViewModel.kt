package com.mctech.stocktradetracking.feature.stock_share.list_position

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToErrorState
import com.mctech.architecture.mvvm.x.core.ktx.changeToListLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.domain.stock_share.interaction.GetMarketStatusCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.SyncStockSharePriceCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.FilterStockListStrategy
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ObserveStockListStrategy
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.SelectStockStrategy
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import com.mctech.stocktradetracking.domain.stock_share_filter.interaction.ObserveCurrentFilterCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.plus

open class StockShareListViewModel constructor(
  private val observeStockListCase: ObserveStockListStrategy,
  private val observeCurrentFilterCase: ObserveCurrentFilterCase,

  private val getMarketStatusCase: GetMarketStatusCase,
  private val selectWorstStockShareCase: SelectStockStrategy,
  private val selectBestStockShareCase: SelectStockStrategy,
  private val syncStockSharePriceCase: SyncStockSharePriceCase,

  private val filterStockListCase: FilterStockListStrategy,
  private val getFinalBalanceCase: ComputeBalanceStrategy
) : BaseViewModel() {

  private val originalStockList = mutableListOf<StockShare>()
  private var realtimeJob: Job? = null
  private var dataTransformerScope: CoroutineScope? = null

  private val _shareList: MutableLiveData<ComponentState<List<StockShare>>> =
    MutableLiveData(ComponentState.Initializing)
  val shareList: LiveData<ComponentState<List<StockShare>>> = _shareList

  private val _stockShareFinalBalance: MutableLiveData<ComponentState<StockShareFinalBalance>> =
    MutableLiveData(ComponentState.Initializing)
  val stockShareFinalBalance: LiveData<ComponentState<StockShareFinalBalance>> =
    _stockShareFinalBalance

  private val _bestStockShare: MutableLiveData<ComponentState<SelectedStock?>> =
    MutableLiveData(ComponentState.Initializing)
  val bestStockShare: LiveData<ComponentState<SelectedStock?>> = _bestStockShare

  private val _worstStockShare: MutableLiveData<ComponentState<SelectedStock?>> =
    MutableLiveData(ComponentState.Initializing)
  val worstStockShare: LiveData<ComponentState<SelectedStock?>> = _worstStockShare

  private val _marketStatus: MutableLiveData<ComponentState<MarketStatus>> =
    MutableLiveData(ComponentState.Initializing)
  val marketStatus: LiveData<ComponentState<MarketStatus>> = _marketStatus

  override suspend fun handleUserInteraction(interaction: UserInteraction) {
    when (interaction) {
      is StockShareListInteraction.LoadStockShare -> {
        loadStockShareListInteraction()
      }
      is StockShareListInteraction.SyncStockPrice -> {
        syncStockPriceInteraction()
      }
      is StockShareListInteraction.StartRealtimePosition -> {
        loadMarketStatusInteraction()
      }
      is StockShareListInteraction.StopRealtimePosition -> {
        stopRealtimePositionInteraction()
      }
    }
  }

  private suspend fun loadStockShareListInteraction() {
    observeStockListCase.execute()
      .onStart {
        _shareList.changeToListLoadingState()
      }
      .catch { exception ->
        _shareList.changeToErrorState(exception)
      }
      .combine(observeCurrentFilterCase.execute()) { list, filter ->
        StockShareListResult(list, filter)
      }
      .collect { result ->
        computeStockScore(result)
        organizeStockListBeforeShowIt(result)
      }
  }

  private suspend fun syncStockPriceInteraction() {
    syncStockSharePriceCase.execute()
  }

  private suspend fun loadMarketStatusInteraction() {
    _marketStatus.changeToLoadingState()
    when (val result = getMarketStatusCase.execute()) {
      is Result.Success -> {

        if (result.result.isOperation) {
          startRealtimePositionInteraction()
        }
        _marketStatus.changeToSuccessState(result.result)
      }
      is Result.Failure -> {
        _marketStatus.changeToErrorState(result.throwable)
      }
    }
  }

  private fun stopRealtimePositionInteraction() {
    realtimeJob?.cancel()
  }

  private suspend fun startRealtimePositionInteraction() {
    stopRealtimePositionInteraction()

    realtimeJob = viewModelScope.async {
      do {
        syncStockPriceInteraction()
        delay(4000)
      } while (isActive)
    }
  }

  private fun organizeStockListBeforeShowIt(result: StockShareListResult) {
    // Filter list
    val stockShareList = filterStockListCase.execute(result.list, result.filter)

    // Refresh adapter.
    originalStockList.clear()
    originalStockList.addAll(stockShareList)

    // Change component state.
    _shareList.changeToSuccessState(stockShareList)
  }

  private fun computeStockScore(result: StockShareListResult) {
    dataTransformerScope?.cancel()

    dataTransformerScope = viewModelScope + Job()

    dataTransformerScope?.async { computeFinalBalance(result.list) }
    dataTransformerScope?.async { computeBestStock(result) }
    dataTransformerScope?.async { computeWorstStock(result) }
  }

  private fun computeFinalBalance(stockShareList: List<StockShare>) {
    _stockShareFinalBalance.changeToLoadingState()
    _stockShareFinalBalance.changeToSuccessState(
      getFinalBalanceCase.execute(stockShareList)
    )
  }

  private fun computeWorstStock(result: StockShareListResult) {
    _worstStockShare.changeToLoadingState()
    _worstStockShare.changeToSuccessState(
      selectWorstStockShareCase.execute(result.list, result.filter)
    )
  }

  private fun computeBestStock(result: StockShareListResult) {
    _bestStockShare.changeToLoadingState()
    _bestStockShare.changeToSuccessState(
      selectBestStockShareCase.execute(result.list, result.filter)
    )
  }

  override fun onCleared() {
    stopRealtimePositionInteraction()
    super.onCleared()
  }
}

private data class StockShareListResult(
  val list: List<StockShare>,
  val filter: StockFilter
)