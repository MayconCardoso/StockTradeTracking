package com.mctech.stocktradetracking.feature.stock_share.view

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
import com.mctech.stocktradetracking.feature.stock_share.StockShareCommand
import com.mctech.stocktradetracking.feature.stock_share.StockShareInteraction
import com.mctech.stocktradetracking.feature.stock_share.StockShareViewModel
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareEditPriceBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StockShareEditPriceFragment : Fragment() {

	private val viewModel : StockShareViewModel by sharedViewModel()
	private var binding   : FragmentStockShareEditPriceBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return FragmentStockShareEditPriceBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindCommand(viewModel){ handleCommands(it) }
		bindData(viewModel.currentStockShare){
			binding?.stockShare = it
			binding?.executePendingBindings()
		}
		bindListeners()
	}

	private fun handleCommands(command: ViewCommand) {
		when(command){
			is StockShareCommand.Back.FromEdit -> {
				findNavController().popBackStack()
			}
		}
	}

	private fun bindListeners() {
		binding?.let { binding ->
			binding.btUpdateStockPrice.setOnClickListener {
				viewModel.interact(
					StockShareInteraction.UpdateStockPrice(
						binding.etShareCode.getValue(),
						binding.etShareAmount.getValue().toInt(),
						binding.etSharePurchasePrice.getValue().toDouble(),
						binding.etSharePrice.getValue().toDouble()
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
