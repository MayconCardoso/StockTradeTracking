package com.mctech.stocktradetracking.feature.stock_share.list_position

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mctech.stocktradetracking.feature.stock_share.databinding.ItemStockShareListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class StockShareListFragment : BaseShareListFragment<ItemStockShareListBinding>() {

	override val viewModel: StockShareListViewModel by sharedViewModel(named("stockShareViewModel"))

    override fun createListItemBinding(parent: ViewGroup, inflater: LayoutInflater): ItemStockShareListBinding {
        return ItemStockShareListBinding.inflate(inflater, parent, false)
    }
}
