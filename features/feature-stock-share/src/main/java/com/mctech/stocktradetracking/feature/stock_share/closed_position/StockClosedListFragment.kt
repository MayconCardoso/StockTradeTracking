package com.mctech.stocktradetracking.feature.stock_share.closed_position

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mctech.stocktradetracking.feature.stock_share.databinding.ItemStockClosedListBinding
import com.mctech.stocktradetracking.feature.stock_share.list_position.BaseShareListFragment
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class StockClosedListFragment : BaseShareListFragment<ItemStockClosedListBinding>() {

  override val viewModel: StockShareListViewModel by sharedViewModel(named("closedStockViewModel"))

  override fun createListItemBinding(
    parent: ViewGroup,
    inflater: LayoutInflater
  ): ItemStockClosedListBinding {
    return ItemStockClosedListBinding.inflate(inflater, parent, false)
  }

}
