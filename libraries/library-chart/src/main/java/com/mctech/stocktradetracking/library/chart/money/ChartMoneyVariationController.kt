package com.mctech.stocktradetracking.library.chart.money

import android.content.Context
import com.mctech.stocktradetracking.library.chart.dp
import com.mctech.stocktradetracking.library.chart.money.ChartMoneyVariationConstants.CIRCLE_RADIOS
import com.mctech.stocktradetracking.library.chart.pixel
import kotlin.math.max
import kotlin.math.min

internal class ChartMoneyVariationController(
  private val context: Context
) {
  /**
   * Data collection set by client with all elements that must be drawn
   */
  private val data by lazy { mutableListOf<MoneyVariationEntry>() }

  /**
   * Resolved data collection with drawn dimensions like height and width
   */
  internal val dataElements by lazy { mutableListOf<MoneyVariationView>() }

  /**
   * Min found value on collection that will be drawn near to the view bottom (height)
   */
  private var lowestDataValue = 0.0

  /**
   * Max found value on collection that will be drawn near to the view top (0)
   */
  private var highestDataValue = 0.0

  /**
   * The size in DP of circle radius used draw a marker in the end of each element.
   */
  private val circleMarkerRadius by lazy { context.dp(CIRCLE_RADIOS) }

  /**
   * The view height size.
   */
  private var viewHeight: Float = 0.0F

  /**
   * The view width size
   */
  private var viewWidth: Int = 0

  /**
   * Computed value that represents the position of R$0.00 on the chart.
   * It is used to defined the path direction and also the correct painter that will be used to draw the line and the circle marker.
   */
  internal var centerZeroY = 0.0F

  /**
   * Computed value that represents the distance between each element (Element width)
   */
  internal var distanceBetweenElements = 0.0F

  /**
   * Called to set the collection that must be drawn on chart.
   */
  internal fun setData(data: List<MoneyVariationEntry>) {
    this.data.clear()
    this.data.addAll(data)

    lowestDataValue = data.minBy { it.amount }?.amount ?: 0.0
    highestDataValue = data.maxBy { it.amount }?.amount ?: 0.0
  }

  /**
   * Called to update the position of each element whenever the view changes its dimensions.
   */
  internal fun updateItemsDimensions(width: Int, height: Int) {
    // Cache view dimensions.
    viewHeight = height.toFloat()
    viewWidth = width

    // The size of each column of the chart.
    distanceBetweenElements = (width - circleMarkerRadius) / data.size

    // Calculate the center of chart.
    centerZeroY = computeCenterZeroY()

    // Reset control
    dataElements.clear()

    // Calculate the position of each element
    data.forEach { data ->
      dataElements.add(
        MoneyVariationView(
          data = data,
          y = computeElementYPosition(data.amount)
        )
      )
    }
  }

  private fun circleSizeOffset() = context.pixel(circleMarkerRadius) * 2

  private fun computeElementYPosition(dataValue: Double): Float {
    // Compute position related to view size and center Y
    val relativePosition = ((dataValue * viewHeight / computeRange()) * -1).toFloat()

    // Return computed value.
    val y = centerZeroY + relativePosition

    // Internal offset padding
    val offset = circleSizeOffset() * 2

    // Apply margin
    return when {
      y < centerZeroY -> max(y, offset)
      y > centerZeroY -> min(viewHeight - offset, y)
      else -> y
    }
  }

  private fun computeRange(): Double {
    return highestDataValue - lowestDataValue
  }

  private fun computeCenterZeroY(): Float {
    // There are only positive values
    if (highestDataValue >= 0 && lowestDataValue >= 0) {
      return viewHeight
    }

    // There are only negative values
    if (highestDataValue < 0 && lowestDataValue < 0) {
      return 0F + circleSizeOffset()
    }

    // There are both values, so I need to find the center based on values
    return (highestDataValue * viewHeight / computeRange()).toFloat() + circleSizeOffset()
  }
}

