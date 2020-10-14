package com.mctech.stocktradetracking.feature.stock_share.add_position

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.feature.stock_share.StockShareNavigator
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareBuyBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockShareBuyFragment : Fragment() {

  private val viewModel: StockShareBuyViewModel by viewModel()
  private val navigator: StockShareNavigator by inject()
  private var binding: FragmentStockShareBuyBinding? = null

  private val textWatcher = object : TextWatcher {
    override fun afterTextChanged(p0: Editable?) = Unit
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
      computeInvestment()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return FragmentStockShareBuyBinding.inflate(inflater, container, false).let {
      binding = it
      binding?.lifecycleOwner = this
      it.root
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    bindCommand(viewModel) { handleCommands(it) }
    bindListeners()
    computeInvestment()
  }

  override fun onDestroyView() {
    binding?.etShareAmount?.removeTextChangedListener(textWatcher)
    binding?.etSharePrice?.removeTextChangedListener(textWatcher)
    super.onDestroyView()
  }

  private fun handleCommands(command: ViewCommand) {
    when (command) {
      is StockShareBuyCommand.NavigateBack -> {
        navigator.navigateBack()
      }
    }
  }

  private fun bindListeners() {
    binding?.etShareAmount?.addTextChangedListener(textWatcher)
    binding?.etSharePrice?.addTextChangedListener(textWatcher)

    binding?.let { binding ->
      binding.btBuy.setOnClickListener {
        viewModel.interact(
          StockShareBuyInteraction.AddPosition(
            binding.etShareCode.getValue(),
            binding.etShareAmount.getValue().toLong(),
            binding.etSharePrice.getValue().toDouble()
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

  private fun computeInvestment() {
    binding?.let { binding ->
      val stockShare = StockShare(
        code = binding.etShareCode.getValue(),
        purchasePrice = binding.etSharePrice.getValue().toDoubleOrNull() ?: 0.0,
        shareAmount = binding.etShareAmount.getValue().toLongOrNull() ?: 0,
        isPositionOpened = true
      )

      binding.itemInvestmentAmount.text = stockShare.getInvestmentValueDescription()
    }
  }
}
