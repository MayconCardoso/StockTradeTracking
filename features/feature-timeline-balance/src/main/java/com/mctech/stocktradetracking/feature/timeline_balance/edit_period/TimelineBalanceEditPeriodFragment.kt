package com.mctech.stocktradetracking.feature.timeline_balance.edit_period

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.feature.timeline_balance.TimelineBalanceNavigator
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.FragmentTimelineEditPeriodBinding
import com.mctech.stocktradetracking.feature.timeline_balance.timelinePeriodFromBundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimelineBalanceEditPeriodFragment : Fragment() {

  private val viewModel: TimelineBalanceEditViewModel by viewModel()
  private val navigator: TimelineBalanceNavigator by inject()
  private var binding: FragmentTimelineEditPeriodBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return FragmentTimelineEditPeriodBinding.inflate(inflater, container, false).let {
      binding = it
      binding?.lifecycleOwner = this
      it.root
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    bindCommand(viewModel) { handleCommands(it) }
    bindState(viewModel.currentPeriodState) { handleCurrentPeriodState(it) }
    bindListeners()
  }

  private fun handleCurrentPeriodState(state: ComponentState<TimelineBalance>) {
    when (state) {
      is ComponentState.Initializing -> {
        viewModel.interact(
          TimelineBalanceEditInteraction.OpenPeriodDetails(
            timelinePeriodFromBundle(requireArguments())
          )
        )
      }
      is ComponentState.Success -> {
        binding?.period = state.result
        binding?.executePendingBindings()
      }
    }
  }

  private fun handleCommands(command: ViewCommand) {
    when (command) {
      is TimelineBalanceEditCommand.NavigationBack -> {
        navigator.navigateBack()
      }
    }
  }

  private fun bindListeners() {
    binding?.let { binding ->
      binding.btSave.setOnClickListener {
        viewModel.interact(
          TimelineBalanceEditInteraction.EditPeriod(
            binding.etPeriodTag.getValue(),
            binding.etInvestment.getValue().toDouble(),
            binding.etProfit.getValue().toDouble(),
            binding.etFinalBalance.getValue().toDouble()
          )
        )

        activity?.currentFocus?.run {
          if (this is EditText) {
            context?.closeKeyboard(this)
          }
        }
      }
    }
  }
}
