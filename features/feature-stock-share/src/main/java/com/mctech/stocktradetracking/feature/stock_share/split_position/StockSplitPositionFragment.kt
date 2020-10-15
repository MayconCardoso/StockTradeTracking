package com.mctech.stocktradetracking.feature.stock_share.split_position

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.feature.stock_share.R
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareSplitBinding
import com.mctech.stocktradetracking.feature.stock_share.stockShareFromBundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockSplitPositionFragment : Fragment() {

  private val viewModel: StockSplitPositionViewModel by viewModel()
  private val navigator: StockShareNavigator by inject()
  private var binding: FragmentStockShareSplitBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return FragmentStockShareSplitBinding.inflate(inflater, container, false).let {
      binding = it
      binding?.lifecycleOwner = this
      it.root
    }
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    bindCommand(viewModel) { handleCommands(it) }
    bindState(viewModel.currentStockShare) { handleStockShareState(it) }
    bindListeners()
  }

  private fun handleStockShareState(state: ComponentState<StockShare>) {
    when (state) {
      is ComponentState.Initializing -> {
        viewModel.interact(
          StockSplitPositionInteraction.OpenStockShareDetails(
            stockShareFromBundle(requireArguments())
          )
        )
      }
      is ComponentState.Success -> {
        binding?.item?.item = state.result
        binding?.executePendingBindings()
        binding?.item?.executePendingBindings()
      }
    }
  }

  private fun handleCommands(command: ViewCommand) {
    when (command) {
      is StockSplitPositionCommand.NavigateBack -> {
        navigator.navigateBack()
      }
    }
  }

  private fun bindListeners() {
    binding?.let { binding ->
      binding.btSplit.setOnClickListener {
        validateForm(binding)
      }
    }
  }

  private fun validateForm(binding: FragmentStockShareSplitBinding) {
    if (isFieldInvalid(binding.tlShareAmount)) {
      return
    }

    confirmationDialog {
      viewModel.interact(
        StockSplitPositionInteraction.SplitStock(
          binding.etShareAmount.getValue().toInt()
        )
      )
    }
  }

  private fun closeKeyboard() {
    activity?.currentFocus?.run {
      if (this is EditText) {
        context?.closeKeyboard(this)
      }
    }
  }

  private fun isFieldInvalid(vararg fields: TextInputLayout): Boolean {
    var isValid = false
    fields.forEach { field ->
      field.error = ""
      if (field.editText?.text?.trim()?.isBlank() == true) {
        field.error = getString(R.string.required)
        isValid = true
      }
    }
    return isValid
  }

  private fun confirmationDialog(blockConfirmation: () -> Unit) {
    closeKeyboard()

    AlertDialog.Builder(requireActivity())
      .setTitle(R.string.confirmation)
      .setMessage(R.string.confirm_split_position)
      .setPositiveButton(R.string.yes) { _, _ ->
        blockConfirmation.invoke()
      }
      .setNegativeButton(R.string.no, null)
      .show()
  }
}
