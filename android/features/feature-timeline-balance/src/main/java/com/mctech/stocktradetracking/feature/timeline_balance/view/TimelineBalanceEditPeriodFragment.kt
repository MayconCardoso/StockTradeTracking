package com.mctech.stocktradetracking.feature.timeline_balance.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindData
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceCommand
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceInteraction
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceViewModel
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.FragmentTimelineEditPeriodBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TimelineBalanceEditPeriodFragment : Fragment() {

	private val viewModel 	: TimelineBalanceViewModel by sharedViewModel()
	private var binding   	: FragmentTimelineEditPeriodBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return FragmentTimelineEditPeriodBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindCommand(viewModel){ handleCommands(it) }
		bindData(viewModel.currentPeriodState){
			binding?.period = it
			binding?.executePendingBindings()
		}
		bindListeners()
	}

	private fun handleCommands(command: ViewCommand) {
		when(command){
			is TimelineBalanceCommand.Back.FromEditPosition -> {
				findNavController().popBackStack()
			}
		}
	}

	private fun bindListeners() {
		binding?.let { binding ->
			binding.btSave.setOnClickListener {
				viewModel.interact(
					TimelineBalanceInteraction.EditPeriod(
						binding.etPeriodTag.getValue(),
						binding.etInvestment.getValue().toDouble(),
						binding.etProfit.getValue().toDouble(),
						binding.etFinalBalance.getValue().toDouble()
					)
				)

				activity?.currentFocus?.run {
					if(this is EditText){
						context?.closeKeyboard(this)
					}
				}
			}
		}
	}
}
