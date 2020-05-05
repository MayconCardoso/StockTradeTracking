package com.mctech.stocksharetracking.feature.stock_share_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.stocksharetracking.feature.stock_share_filter.databinding.FragmentStockShareFilterBinding
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockShareFilterFragment : Fragment(){
    private val viewModel 	: StockShareFilterViewModel 		by viewModel()
    private val navigator   : StockShareFilterNavigator         by inject()
    private var binding   	: FragmentStockShareFilterBinding? 	= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentStockShareFilterBinding.inflate(inflater, container, false).let {
            binding = it
            binding?.lifecycleOwner = this
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindCommand(viewModel){ handleCommands(it) }
        bindState(viewModel.currentFilter){ handleCurrentFilter(it) }
        bindListeners()
    }

    private fun handleCurrentFilter(filterState: ComponentState<StockFilter>) {
        when(filterState){
            is ComponentState.Success -> {
                setupCurrentFilterOnScreen(filterState.result)
            }
        }
    }

    private fun handleCommands(command: ViewCommand) {
        when(command){
            is StockShareFilterCommand.NavigateBack -> navigator.navigateBack()
        }
    }

    private fun bindListeners() {
        binding?.apply {
            btSalver.setOnClickListener {
                saveCurrentFilterFromScreen(this)
            }
        }
    }


    private fun setupCurrentFilterOnScreen(result: StockFilter) {
        binding?.cbGroup?.isChecked = result.isGroupingStock

        binding?.rankingRadioGroup?.check(
            when(result.rankingQualifier){
                RankingQualifier.Percent -> R.id.rankingVariation
                RankingQualifier.Balance -> R.id.rankingProfit
            }
        )

        binding?.sortRadioGroup?.check(
            when(result.sort){
                FilterSort.BalanceAsc   -> R.id.sortProfitL
                FilterSort.BalanceDesc  -> R.id.sortProfitH
                FilterSort.PercentAsc   -> R.id.sortVariationL
                FilterSort.PercentDesc  -> R.id.sortVariationH
                FilterSort.NameAsc      -> R.id.sortNameA
                FilterSort.NameDesc     -> R.id.sortNameZ
            }
        )
    }

    private fun saveCurrentFilterFromScreen(binding: FragmentStockShareFilterBinding) {
        viewModel.interact(StockShareFilterInteraction.ApplyFilter(
            StockFilter(
                isGroupingStock  = binding.cbGroup.isChecked,
                rankingQualifier =  when(binding.rankingRadioGroup.checkedRadioButtonId){
                    R.id.rankingVariation -> RankingQualifier.Percent
                    else -> RankingQualifier.Balance
                },
                sort = when(binding.sortRadioGroup.checkedRadioButtonId){
                    R.id.sortProfitL    -> FilterSort.BalanceAsc
                    R.id.sortProfitH    -> FilterSort.BalanceDesc
                    R.id.sortVariationL -> FilterSort.PercentAsc
                    R.id.sortVariationH -> FilterSort.PercentDesc
                    R.id.sortNameA      -> FilterSort.NameAsc
                    else                -> FilterSort.NameDesc
                }
            )
        ))
    }
}