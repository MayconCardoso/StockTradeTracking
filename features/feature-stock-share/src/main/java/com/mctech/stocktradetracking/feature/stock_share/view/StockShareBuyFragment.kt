package com.mctech.stocktradetracking.feature.stock_share.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.feature.stock_share.StockShareCommand
import com.mctech.stocktradetracking.feature.stock_share.StockShareInteraction
import com.mctech.stocktradetracking.feature.stock_share.StockShareViewModel
import com.mctech.stocktradetracking.feature.stock_share.databinding.FragmentStockShareBuyBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StockShareBuyFragment : Fragment() {

	private val viewModel : StockShareViewModel by sharedViewModel()
	private var binding   : FragmentStockShareBuyBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return FragmentStockShareBuyBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.viewModel = viewModel
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindCommand(viewModel){ handleCommands(it) }
		bindListeners()
	}

	private fun handleCommands(command: ViewCommand) {
		when(command){
			is StockShareCommand.BackFromAddPosition -> {
				findNavController().popBackStack()
			}
		}
	}

	private fun bindListeners() {
		binding?.let { binding ->
			binding.btBuy.setOnClickListener {
				viewModel.interact(
					StockShareInteraction.AddPosition(
						binding.etShareCode.getValue(),
						binding.etShareAmount.getValue().toInt(),
						binding.etSharePrice.getValue().toDouble()
					)
				)

				context?.closeKeyboard()
			}
		}
	}
}
