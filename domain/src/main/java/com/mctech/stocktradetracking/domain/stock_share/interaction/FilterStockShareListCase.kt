package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.FilterStockListStrategy
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort

class FilterStockShareListCase(
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
			stockShareList.sortedBy { it.getBalance() }
		}
		FilterSort.BalanceDesc -> {
			stockShareList.sortedByDescending { it.getBalance() }
		}
		FilterSort.PercentAsc -> {
			stockShareList.sortedBy { it.getVariation() }
		}
		FilterSort.PercentDesc -> {
			stockShareList.sortedByDescending { it.getVariation() }
		}
	}
}