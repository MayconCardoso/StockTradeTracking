package com.mctech.stocktradetracking.feature.stock_share

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class StockShareFragment : Fragment() {
	private lateinit var viewModel : StockShareViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bindCommand(viewModel){ handleCommand(it) }
		bindState(viewModel.shareList){ handleShareListState(it) }
	}

	private fun handleCommand(it: ViewCommand) {
		when(it){

		}
	}

	private fun handleShareListState(state: ComponentState<StockShare>) {
		when(state){
			is ComponentState.Initializing -> TODO()
			is ComponentState.Loading -> TODO()
			is ComponentState.Error -> TODO()
			is ComponentState.Success -> TODO()
		}
	}

}
