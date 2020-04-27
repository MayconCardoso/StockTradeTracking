package com.mctech.stocktradetracking.feature.stock_share.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.view.ktx.attachSimpleData
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.feature.stock_share.R
import com.mctech.stocktradetracking.feature.stock_share.StockShareInteraction
import com.mctech.stocktradetracking.feature.stock_share.StockShareViewModel
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareListBinding
import com.mctech.stocktradetracking.feature.stock_share.databinding.ItemStockShareListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StockShareListFragment : Fragment() {

	private val viewModel : StockShareViewModel by sharedViewModel()
	private var binding   : FragmentStockShareListBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		setHasOptionsMenu(true)

		return FragmentStockShareListBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.viewModel = viewModel
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindState(viewModel.shareList){ handleShareListState(it) }
		bindState(viewModel.stockShareFinalBalance){ handleFinalBalanceState(it) }
		bindListeners()
	}

	override fun onResume() {
		super.onResume()
		viewModel.interact(StockShareInteraction.List.LoadStockShare)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.stock_share_tracking_menu, menu)
	}

	private fun handleFinalBalanceState(state: ComponentState<StockShareFinalBalance>) {
		when(state){
			is ComponentState.Success -> {
				binding?.finalBalance = state.result
				binding?.executePendingBindings()
			}
		}
	}

	private fun handleShareListState(state: ComponentState<List<StockShare>>) {
		when(state){
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

	private fun bindListeners() {
		binding?.btBuy?.setOnClickListener {
			findNavController().navigate(
				R.id.action_stockShareListFragment_to_stockShareBuyFragment
			)
		}
	}
}
