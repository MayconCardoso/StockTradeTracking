package com.mctech.stocktradetracking.feature.stock_share.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.view.ktx.attachSimpleData
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.feature.stock_share.StockShareInteraction
import com.mctech.stocktradetracking.feature.stock_share.StockShareViewModel
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareBinding
import com.mctech.stocktradetracking.feature.stock_share.databinding.ItemStockShareListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StockShareListFragment : Fragment() {

	private val viewModel : StockShareViewModel by sharedViewModel()
	private var binding   : FragmentStockShareBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return FragmentStockShareBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.viewModel = viewModel
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindState(viewModel.shareList){ handleShareListState(it) }
	}

	private fun handleShareListState(state: ComponentState<List<StockShare>>) {
		when(state){
			is ComponentState.Initializing -> {
				viewModel.interact(StockShareInteraction.List.LoadStockShare)
			}
			is ComponentState.Success -> {
				renderStockList(state.result)
			}
		}
	}

	private fun renderStockList(result: List<StockShare>) {
		binding?.recyclerView?.attachSimpleData(
			items = result,
			viewBindingCreator = { parent, inflater ->
				ItemStockShareListBinding.inflate(inflater, parent, false)
			},
			prepareHolder = { item, viewBinding, _ ->
				viewBinding.item = item
				viewBinding.root.setOnClickListener {
					viewModel.interact(StockShareInteraction.List.OpenStockShareDetails(item))
				}
			},
			updateCallback = object : DiffUtil.ItemCallback<StockShare>() {
				override fun areItemsTheSame(left: StockShare, right: StockShare) = left.id == right.id

				override fun areContentsTheSame(left: StockShare, right: StockShare): Boolean {
					return left.code == right.code
							&& left.getBuyDescription() == right.getBuyDescription()
							&& left.getBalanceDescription() == right.getBalanceDescription()
							&& left.getVariationDescription() == right.getVariationDescription()
				}
			}
		)
	}
}
