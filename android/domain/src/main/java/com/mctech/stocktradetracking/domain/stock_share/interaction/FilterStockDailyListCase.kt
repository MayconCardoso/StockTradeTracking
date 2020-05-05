package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.FilterStockListStrategy
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort

class FilterStockDailyListCase(
	groupStockShareListCase: GroupStockShareListCase
) : FilterStockListStrategy(groupStockShareListCase) {

	override fun sort(stockShareList: List<StockShare>, filterSort: FilterSort) = when(filterSort){
		FilterSort.NameAsc -> {
			stockShareList.sortedBy { it.code }
		}
		FilterSort.NameDesc -> {
			stockShareList.sortedByDescending { it.code }
		}
		FilterSort.BalanceAsc -> {
			stockShareList.sortedBy { it.getDailyVariationBalance() }
		}
		FilterSort.BalanceDesc -> {
			stockShareList.sortedByDescending { it.getDailyVariationBalance() }
		}
		FilterSort.PercentAsc -> {
			stockShareList.sortedBy { it.getDailyVariation() }
		}
		FilterSort.PercentDesc -> {
			stockShareList.sortedByDescending { it.getDailyVariation() }
		}
	}
}