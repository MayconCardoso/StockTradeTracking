package com.mctech.stocktradetracking.feature.timeline_balance.list_period

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.view.ktx.attachSimpleDataBindingData
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.feature.timeline_balance.R
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceNavigator
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.FragmentTimelineBalanceBinding
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.ItemTimelinePeriodListBinding
import com.mctech.stocktradetracking.library.chart.money.MoneyVariationEntry
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TimelineBalanceListFragment : Fragment() {
  private val viewModel: TimelineBalanceListViewModel by sharedViewModel()
  private val navigator: TimelineBalanceNavigator by inject()
  private var binding: FragmentTimelineBalanceBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
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
    when (item.itemId) {
      R.id.menu_add -> {
        navigateToAddPositionFlow()
      }
    }
    return true
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    bindState(viewModel.periodList) { handlePeriodListState(it) }
    bindState(viewModel.finalBalance) { handleFinalBalanceState(it) }
    bindState(viewModel.balanceChartList) { handleBalanceChartState(it) }
    bindListeners()
  }

  override fun onStart() {
    super.onStart()
    viewModel.interact(TimelineBalanceListInteraction.LoadTimelineComponent)
  }

  private fun handleFinalBalanceState(state: ComponentState<TimelineBalance?>) {
    when (state) {
      is ComponentState.Success -> {
        binding?.finalBalance = state.result
        binding?.executePendingBindings()
      }
    }
  }

  private fun handlePeriodListState(state: ComponentState<List<TimelineBalance>>) {
    when (state) {
      is ComponentState.Success -> {
        renderStockList(state.result)
      }
    }
  }

  private fun handleBalanceChartState(state: ComponentState<List<TimelineBalance>>) {
    when (state) {
      is ComponentState.Success -> {
        binding?.chartView?.setData(state.result.map {
          MoneyVariationEntry(it.getFinalProfit())
        })
      }
    }
  }

  private fun renderStockList(result: List<TimelineBalance>) {
    binding?.recyclerView?.attachSimpleDataBindingData(
      items = result,
      viewBindingCreator = { parent, inflater ->
        ItemTimelinePeriodListBinding.inflate(inflater, parent, false)
      },
      prepareHolder = { item, viewBinding, _ ->
        viewBinding.item = item
        viewBinding.cardItem.setOnClickListener {
          navigateToEditPosition(item)
        }
      },
      updateCallback = object : DiffUtil.ItemCallback<TimelineBalance>() {
        override fun areItemsTheSame(left: TimelineBalance, right: TimelineBalance) =
          left.periodTag == right.periodTag

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
    navigator.fromTimelineToOpenPeriod()
  }

  private fun navigateToEditPosition(item: TimelineBalance) {
    navigator.fromTimelineToEditPeriod(item)
  }
}
