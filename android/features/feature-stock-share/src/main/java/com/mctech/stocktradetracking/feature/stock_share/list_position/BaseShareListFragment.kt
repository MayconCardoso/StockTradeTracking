package com.mctech.stocktradetracking.feature.stock_share.list_position

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.card.MaterialCardView
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.view.ktx.attachSimpleData
import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.feature.stock_share.BR
import com.mctech.stocktradetracking.feature.stock_share.R
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareListBinding
import org.koin.android.ext.android.inject

abstract class BaseShareListFragment<IDB : ViewDataBinding> : Fragment() {

	private var binding 	: FragmentStockShareListBinding? = null
	private val navigator 	: StockShareNavigator by inject()

	abstract val viewModel 	: StockShareListViewModel

	abstract fun createListItemBinding(
		parent: ViewGroup,
		inflater: LayoutInflater
	): IDB

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		setHasOptionsMenu(true)

		return FragmentStockShareListBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.setVariable(BR.viewModel, viewModel)
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindState(viewModel.shareList){ handleShareListState(it) }
		bindState(viewModel.stockShareFinalBalance){ handleFinalBalanceState(it) }
		bindState(viewModel.bestStockShare){ handleBestStockState(it) }
		bindState(viewModel.worstStockShare){ handleWorstStockState(it) }

		binding?.btBuy?.setOnClickListener {
			navigator.fromStockListToBuyPosition()
		}

		binding?.swipeRefreshLayout?.setOnRefreshListener {
			viewModel.interact(StockShareListInteraction.SyncStockPrice)
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.interact(StockShareListInteraction.StartRealtimePosition)
	}

	override fun onStop() {
		viewModel.interact(StockShareListInteraction.StopRealtimePosition)
		super.onStop()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.stock_share_tracking_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId){
			R.id.menu_add -> {
				navigator.fromStockListToBuyPosition()
			}
			R.id.menu_filter -> {
				navigator.fromStockListToFilter()
			}
			R.id.menu_chart -> {
				Toast.makeText(requireContext(), "Developing...", Toast.LENGTH_SHORT).show()
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

	private fun handleWorstStockState(state: ComponentState<SelectedStock?>) {
		when(state){
			is ComponentState.Success -> {
				binding?.worstStock = state.result
				binding?.executePendingBindings()
			}
		}
	}

	private fun handleBestStockState(state: ComponentState<SelectedStock?>) {
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
				viewModel.interact(StockShareListInteraction.LoadStockShare)
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
				createListItemBinding(parent, inflater)
			},
			prepareHolder = { item, viewBinding, _ ->
				viewBinding.setVariable(BR.item, item)
				viewBinding.root.findViewById<MaterialCardView>(R.id.cardItem).setOnClickListener {
					navigator.fromStockListToEditPosition(item)
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
