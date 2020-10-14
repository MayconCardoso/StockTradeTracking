package com.mctech.stocktradetracking.feature.stock_share.edit_position

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.feature.stock_share.R
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareEditPriceBinding
import com.mctech.stocktradetracking.feature.stock_share.stockShareFromBundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockShareEditPositionFragment : Fragment() {

  private val viewModel: StockShareEditPositionViewModel by viewModel()
  private val navigator: StockShareNavigator by inject()
  private var binding: FragmentStockShareEditPriceBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)

    return FragmentStockShareEditPriceBinding.inflate(inflater, container, false).let {
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

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.stock_share_delete_menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_delete -> confirmationDialog(R.string.question_delete) {
        viewModel.interact(StockShareEditPositionInteraction.DeleteStockShare)
      }
      R.id.menu_close_item -> confirmationDialog(R.string.question_close) {
        viewModel.interact(
          StockShareEditPositionInteraction.CloseStockPosition(
            binding?.etSharePrice?.getValue()?.toDouble()
          )
        )
      }
      R.id.menu_spit_item -> {
        viewModel.interact(StockShareEditPositionInteraction.SplitStockShare)
      }
    }

    return true
  }

  private fun handleStockShareState(state: ComponentState<StockShare>) {
    when (state) {
      is ComponentState.Initializing -> {
        viewModel.interact(
          StockShareEditPositionInteraction.OpenStockShareDetails(
            stockShareFromBundle(requireArguments())
          )
        )
      }
      is ComponentState.Success -> {
        binding?.stockShare = state.result
        binding?.executePendingBindings()
      }
    }
  }

  private fun handleCommands(command: ViewCommand) {
    when (command) {
      is StockShareEditPositionCommand.NavigateBack -> {
        navigator.navigateBack()
      }
      is StockShareEditPositionCommand.NavigateToSplitScreen -> {
        navigator.fromEditToSplitPosition(command.stock)
      }
    }
  }

  private fun bindListeners() {
    binding?.let { binding ->
      binding.btUpdateStockPrice.setOnClickListener {
        viewModel.interact(
          StockShareEditPositionInteraction.UpdateStockPrice(
            binding.etShareCode.getValue(),
            binding.etShareAmount.getValue().toLong(),
            binding.etSharePurchasePrice.getValue().toDouble(),
            binding.etSharePrice.getValue().toDouble()
          )
        )

        closeKeyboard()
      }
    }
  }

  private fun closeKeyboard() {
    activity?.currentFocus?.run {
      if (this is EditText) {
        context?.closeKeyboard(this)
      }
    }
  }

  private fun confirmationDialog(question: Int, blockConfirmation: () -> Unit) {
    closeKeyboard()

    AlertDialog.Builder(requireActivity())
      .setTitle(R.string.confirmation)
      .setMessage(question)
      .setPositiveButton(R.string.yes) { _, _ ->
        blockConfirmation.invoke()
      }
      .setNegativeButton(R.string.no, null)
      .show()
  }
}
