package com.mctech.stocktradetracking.domain.stock_share_filter.entity

import java.io.Serializable

data class StockFilter(
    val isGroupingStock : Boolean,
    val sort : FilterSort,
    val rankingQualifier: RankingQualifier
) : Serializable {

    companion object Default {
        operator fun invoke() = StockFilter(
            isGroupingStock = true,
            sort = FilterSort.BalanceDesc,
            rankingQualifier = RankingQualifier.Balance
        )
    }

}

enum class FilterSort(id : Int) : Serializable{
    NameAsc(1),
    NameDesc(2),
    PercentAsc(3),
    PercentDesc(4),
    BalanceAsc(5),
    BalanceDesc(6)
}

enum class RankingQualifier(id : Int){
    Balance(1),
    Percent(2)
}