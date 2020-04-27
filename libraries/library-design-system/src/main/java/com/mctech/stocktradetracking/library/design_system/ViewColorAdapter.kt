package com.mctech.stocktradetracking.library.design_system

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("priceBackgroundColor", requireAll = false)
fun View.priceBackgroundColor(value : Double?) {
    value?.let {
        val color = ContextCompat.getColor(context, if(value >= 0)
            R.color.colorSuccessDark
        else
            R.color.colorDangerDark
        )

        setBackgroundColor(color)
    }
}

@BindingAdapter("priceTextColor", requireAll = false)
fun TextView.priceTextColor(value : Double?) {
    value?.let {
        val color = ContextCompat.getColor(context, if(value >= 0)
            R.color.colorSuccessDark
        else
            R.color.colorDangerDark
        )

        setTextColor(color)
    }
}