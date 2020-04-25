package com.mctech.stocktradetracking.feature.stock_share.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.feature.stock_share.StockShareInteraction
import com.mctech.stocktradetracking.feature.stock_share.StockShareViewModel

class StockShareListFragment : Fragment() {
	private lateinit var viewModel : StockShareViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bindState(viewModel.shareList){ handleShareListState(it) }
	}

	private fun handleShareListState(state: ComponentState<StockShare>) {
		when(state){
			is ComponentState.Initializing -> {
				viewModel.interact(StockShareInteraction.List.LoadStockShare)
			}
		}
	}
}
