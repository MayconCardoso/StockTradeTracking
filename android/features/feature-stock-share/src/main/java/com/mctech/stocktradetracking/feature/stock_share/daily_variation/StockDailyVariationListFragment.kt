package com.mctech.stocktradetracking.feature.stock_share.daily_variation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mctech.stocktradetracking.feature.stock_share.databinding.ItemStockDailyVariationListBinding
import com.mctech.stocktradetracking.feature.stock_share.list_position.BaseShareListFragment
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class StockDailyVariationListFragment  : BaseShareListFragment<ItemStockDailyVariationListBinding>() {

	override val viewModel: StockShareListViewModel by sharedViewModel(named("dailyStockViewModel"))

	override fun createItemBinding(parent: ViewGroup, inflater: LayoutInflater): ItemStockDailyVariationListBinding {
		return ItemStockDailyVariationListBinding.inflate(inflater, parent, false)
	}

}
