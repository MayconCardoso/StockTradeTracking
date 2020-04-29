package com.mctech.stocktradetracking.feature.timeline_balance.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.view.ktx.attachSimpleData
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.feature.timeline_balance.R
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceInteraction
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceViewModel
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.FragmentTimelineBalanceBinding
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.ItemTimelinePeriodListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TimelineBalanceFragment : Fragment() {
	private val viewModel : TimelineBalanceViewModel by sharedViewModel()
	private var binding   : FragmentTimelineBalanceBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		setHasOptionsMenu(true)

		return FragmentTimelineBalanceBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.viewModel = viewModel
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.timeline_balance_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId){
			R.id.menu_add -> {
				navigateToAddPositionFlow()
			}
		}
		return true
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindState(viewModel.periodList){ handlePeriodListState(it) }
		bindState(viewModel.finalBalance){ handleFinalBalanceState(it) }
		bindListeners()
	}

	private fun handleFinalBalanceState(finalBalanceState: ComponentState<TimelineBalance>) {
		when(finalBalanceState){
			is ComponentState.Success -> {
				binding?.finalBalance = finalBalanceState.result
				binding?.executePendingBindings()
			}
		}
	}

	private fun handlePeriodListState(state: ComponentState<List<TimelineBalance>>) {
		when(state){
			is ComponentState.Initializing -> {
				viewModel.interact(TimelineBalanceInteraction.LoadTimelineComponent)
			}
			is ComponentState.Success -> {
				renderStockList(state.result)
			}
		}
	}

	private fun renderStockList(result: List<TimelineBalance>) {
		binding?.recyclerView?.attachSimpleData(
			items = result,
			viewBindingCreator = { parent, inflater ->
				ItemTimelinePeriodListBinding.inflate(inflater, parent, false)
			},
			prepareHolder = { item, viewBinding, _ ->
				viewBinding.item = item
				viewBinding.cardItem.setOnClickListener {
					viewModel.interact(TimelineBalanceInteraction.OpenPeriodDetails(item))
					//findNavController().navigate(R.id.action_stockShareListFragment_to_stockShareEditPriceFragment)
				}
			},
			updateCallback = object : DiffUtil.ItemCallback<TimelineBalance>() {
				override fun areItemsTheSame(left: TimelineBalance, right: TimelineBalance) = left.periodTag == right.periodTag

				override fun areContentsTheSame(left: TimelineBalance, right: TimelineBalance): Boolean {
					return left.periodTag == right.periodTag
							&& left.periodProfit == right.periodProfit
							&& left.periodInvestment == right.periodInvestment
							&& left.getFinalBalance() == right.getFinalBalance()
				}
			}
		)
	}

	private fun bindListeners() {
		binding?.btAddPeriod?.setOnClickListener {
			navigateToAddPositionFlow()
		}
	}

	private fun navigateToAddPositionFlow() {
		findNavController().navigate(
			R.id.action_timelineBalanceFragment_to_timelineBalanceAddPeriodFragment
		)
	}
}
