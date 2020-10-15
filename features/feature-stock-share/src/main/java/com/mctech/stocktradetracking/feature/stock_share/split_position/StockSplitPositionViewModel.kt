package com.mctech.stocktradetracking.feature.stock_share.split_position

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.SplitStockShareCase

class StockSplitPositionViewModel constructor(
  private val splitStockShareCase: SplitStockShareCase
) : BaseViewModel() {
  private var currentStock: StockShare? = null

  private val _currentStockShare: MutableLiveData<ComponentState<StockShare>> =
    MutableLiveData(ComponentState.Initializing)
  val currentStockShare: LiveData<ComponentState<StockShare>> = _currentStockShare

  override suspend fun handleUserInteraction(interaction: UserInteraction) {
    when (interaction) {
      is StockSplitPositionInteraction.OpenStockShareDetails -> openStockShareInteraction(
        interaction.item
      )
      is StockSplitPositionInteraction.SplitStock -> splitStockInteraction(
        interaction.splitRatio
      )
    }
  }

  private suspend fun splitStockInteraction(splitRatio: Int) {
    // Split stock
    currentStock?.let {stock ->
      splitStockShareCase.execute(stock, splitRatio)
    }

    // Navigate back
    sendCommand(StockSplitPositionCommand.NavigateBack)
  }

  private fun openStockShareInteraction(item: StockShare) {
    _currentStockShare.changeToSuccessState(item)
    currentStock = item
  }

}
