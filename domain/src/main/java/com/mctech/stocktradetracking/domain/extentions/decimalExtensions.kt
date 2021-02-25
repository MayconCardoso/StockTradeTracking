package com.mctech.stocktradetracking.domain.extentions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Locale

fun Double.formatBrazilianCurrency(): String {
  if (this == 0.0) {
    return "R$0,00"
  }

  val formatter = DecimalFormat.getInstance(Locale.GERMAN).apply {
    (this as DecimalFormat).applyPattern("R$###,###,###.00")
  }
  return formatter.format(this)
}

fun Double.round(decimals: Int = 2): Double {
  return BigDecimal(this).setScale(decimals, RoundingMode.HALF_EVEN).toDouble()
}

fun Double.toPercent(): String {
  return "${this}%"
}