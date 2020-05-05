package com.mctech.stocktradetracking.feature.stock_share.add_position

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class StockShareBuyCommand : ViewCommand {
    object NavigateBack : StockShareBuyCommand()
}