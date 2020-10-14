package com.mctech.stocktradetracking.feature.stock_share

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.library.design_system.PulseView


@BindingAdapter("changeMarketState", requireAll = false)
fun PulseView.changeMarketState(marketStatus: ComponentState<MarketStatus>) {
  when (marketStatus) {
    is ComponentState.Success -> {
      startPulse()
      if (marketStatus.result.isOperation) {
        changeViewColor(R.color.colorSuccessDark)
      } else {
        changeViewColor(R.color.colorDangerDark)
      }
    }
    else -> {
      stopPulse()
    }
  }
}

@BindingAdapter("changeMarketStateMessage", requireAll = false)
fun TextView.changeMarketStateMessage(marketStatus: ComponentState<MarketStatus>) {
  when (marketStatus) {
    is ComponentState.Success -> {
      text = marketStatus.result.message
    }
  }
}