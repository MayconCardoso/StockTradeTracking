package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.architecture.mvvm.x.core.ViewCommand

sealed class StockShareCommand : ViewCommand {
    sealed class Back : StockShareCommand(){
        object FromBuy : Back()
        object FromEdit : Back()
    }
}