package com.mctech.stocksharetracking.feature.stock_share_filter

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class StockShareFilterCommand : ViewCommand {
  object NavigateBack : StockShareFilterCommand()
}