package com.mctech.stocktradetracking.feature.stock_share.list

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
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareListBinding
import com.mctech.stocktradetracking.feature.stock_share.databinding.ItemStockShareListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StockShareListFragment : Fragment() {

	private val viewModel : StockShareListViewModel by sharedViewModel()
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
		bindState(viewModel.bestStockShare){ handleBestStockState(it) }
		bindState(viewModel.worstStockShare){ handleWorstStockState(it) }
		bindListeners()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.stock_share_tracking_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId){
			R.id.menu_add -> {
				navigateToBuyFlow()
			}
			R.id.menu_filter -> {
				viewModel.interact(StockShareListInteraction.List.ChangeListFilter(true))
			}
		}

		return true
	}

	private fun handleFinalBalanceState(state: ComponentState<StockShareFinalBalance>) {
		when(state){
			is ComponentState.Success -> {
				binding?.finalBalance = state.result
				binding?.executePendingBindings()
			}
		}
	}

	private fun handleWorstStockState(state: ComponentState<StockShare>) {
		when(state){
			is ComponentState.Success -> {
				binding?.worstStock = state.result
				binding?.executePendingBindings()
			}
		}
	}

	private fun handleBestStockState(state: ComponentState<StockShare>) {
		when(state){
			is ComponentState.Success -> {
				binding?.bestStock = state.result
				binding?.executePendingBindings()
			}
		}
	}

	private fun handleShareListState(state: ComponentState<List<StockShare>>) {
		binding?.swipeRefreshLayout?.isRefreshing = state is ComponentState.Loading.FromData

		when(state){
			is ComponentState.Initializing -> {
				viewModel.interact(StockShareListInteraction.List.LoadStockShare)
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
				viewBinding.cardItem.setOnClickListener {
					openStockShare(item)
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
			navigateToBuyFlow()
		}

		binding?.swipeRefreshLayout?.setOnRefreshListener {
			viewModel.interact(StockShareListInteraction.SyncStockPrice)
		}
	}

	private fun navigateToBuyFlow() {
		val destination = StockShareListFragmentDirections.actionStockShareListFragmentToStockShareBuyFragment()
		findNavController().navigate(destination)
	}

	private fun openStockShare(stockShare: StockShare) {
		val destination = StockShareListFragmentDirections.actionStockShareListFragmentToStockShareEditPriceFragment(
			stockShare
		)
		findNavController().navigate(destination)
	}
}
