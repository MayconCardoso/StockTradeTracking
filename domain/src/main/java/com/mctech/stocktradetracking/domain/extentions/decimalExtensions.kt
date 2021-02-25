package com.mctech.stocktradetracking.domain.extentions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Locale

fun Double.formatBrazilianCurrency(): String {
  // Simple format was not working on CI for some reason.
  // So temporarily we need to do this whole thing to make it possible to run CI.
  val numberFormat = DecimalFormat.getCurrencyInstance(Locale.GERMAN)
  val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols

  // Remove symbol
  decimalFormatSymbols.currencySymbol = ""

  // Setup formatter
  numberFormat.decimalFormatSymbols = decimalFormatSymbols

  val formatNumber = numberFormat
    .format(this)
    .replace("\\s".toRegex(), "")

  return if(this >= 0) {
    "R$$formatNumber"
  }
  else{
    "-R$${formatNumber.substring(1)}"
  }
}

fun Double.round(decimals: Int = 2): Double {
  return BigDecimal(this).setScale(decimals, RoundingMode.HALF_EVEN).toDouble()
}

fun Double.toPercent(): String {
  return "${this}%"
}