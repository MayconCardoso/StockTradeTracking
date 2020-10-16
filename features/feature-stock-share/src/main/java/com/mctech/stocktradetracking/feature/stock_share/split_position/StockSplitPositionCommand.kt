package com.mctech.stocktradetracking.feature.stock_share.split_position

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class StockSplitPositionCommand : ViewCommand {
  object NavigateBack : StockSplitPositionCommand()
}