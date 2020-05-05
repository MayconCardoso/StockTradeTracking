package com.mctech.stocktradetracking.feature.stock_share.edit_position

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class StockShareEditPositionCommand : ViewCommand {
    object NavigateBack : StockShareEditPositionCommand()
}