package com.mctech.stocktradetracking.feature.timeline_balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.FragmentTimelineBalanceBinding
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

	private fun handlePeriodListState(state: ComponentState<List<TimelineBalance>>) {
		when(state){
			is ComponentState.Initializing -> TODO()
			is ComponentState.Loading -> TODO()
			is ComponentState.Error -> TODO()
			is ComponentState.Success -> TODO()
		}
	}

}
