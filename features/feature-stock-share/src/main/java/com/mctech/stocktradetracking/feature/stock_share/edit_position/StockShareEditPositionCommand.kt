package com.mctech.stocktradetracking.feature.stock_share.edit_position

import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

sealed class StockShareEditPositionCommand : ViewCommand {
  object NavigateBack : StockShareEditPositionCommand()
  data class NavigateToSplitScreen(
    val stock: StockShare
  ) : StockShareEditPositionCommand()
}