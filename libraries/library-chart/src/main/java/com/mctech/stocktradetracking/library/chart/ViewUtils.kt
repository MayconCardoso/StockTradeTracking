package com.mctech.stocktradetracking.library.chart

import android.content.Context
import android.view.View

fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
  val specMode = View.MeasureSpec.getMode(measureSpec)
  val specSize = View.MeasureSpec.getSize(measureSpec)

  return when (specMode) {
    View.MeasureSpec.EXACTLY -> specSize
    View.MeasureSpec.AT_MOST -> desiredSize.coerceAtMost(specSize)
    else -> desiredSize
  }
}

fun Context.dp(value: Int): Float {
  val density = resources.displayMetrics.density
  return value * density
}

fun Context.pixel(value: Float): Float {
  val density = resources.displayMetrics.density
  return value / density
}
