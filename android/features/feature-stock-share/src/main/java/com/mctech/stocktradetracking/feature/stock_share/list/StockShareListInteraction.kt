package com.mctech.stocktradetracking.feature.stock_share.list

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class StockShareListInteraction : UserInteraction {

    sealed class List : StockShareListInteraction() {
        object LoadStockShare : List()
        data class ChangeListFilter(val groupShares : Boolean) : List()
    }

    object SyncStockPrice : StockShareListInteraction()
}