package com.mctech.stocktradetracking.library.design_system

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("priceBackgroundColor", requireAll = false)
fun View.priceBackgroundColor(value: Double?) {
    value?.let {
        setBackgroundColor(ContextCompat.getColor(context, value.getMoneyColor()))
    }
}

@BindingAdapter("priceTextColor", requireAll = false)
fun TextView.priceTextColor(value: Double?) {
    value?.let {
        setTextColor(ContextCompat.getColor(context, value.getMoneyColor()))
    }
}


private fun Double.getMoneyColor() : Int{
    return if(this > 0){
        R.color.colorSuccessDark
    } else if(this < 0 ){
        R.color.colorDangerDark
    }
    else{
        R.color.colorNeutral
    }
}