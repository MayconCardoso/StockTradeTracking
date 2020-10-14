package com.mctech.stocktradetracking.library.chart.money

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mctech.stocktradetracking.library.chart.R
import com.mctech.stocktradetracking.library.chart.measureDimension
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.sqrt


class ChartMoneyVariationView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

  // Data values
  private val data by lazy { mutableListOf<MoneyVariationEntry>() }
  private val dataElements by lazy { mutableListOf<MoneyVariationView>() }
  private var minDataValue = 0.0
  private var maxDataValue = 0.0

  // View values
  private val suggestionHeight by lazy { 100.dp().toInt() }
  private val variationLineSize by lazy { 2.dp() }
  private val circleMarkerRadius by lazy { 3.dp() }
  private var distanceBetweenElements = 0.0F
  private var currentXPosition = 0.0F
  private var currentYPosition = 0.0F
  private var centerZeroY = 0.0F

  // Painters
  private val positivePaint by lazy { defaultLinePaint(positiveColor) }
  private val negativePaint by lazy { defaultLinePaint(negativeColor) }
  private val centerPainter by lazy {
    defaultLinePaint(R.color.colorCenterChartLine).apply {
      strokeWidth = 1.dp()
    }
  }

  var positiveColor: Int = R.color.colorPositiveValue
    set(value) {
      field = value
      updatePaintColor(positivePaint, value)
    }
  var negativeColor: Int = R.color.colorNegativeValue
    set(value) {
      field = value
      updatePaintColor(negativePaint, value)
    }


  init {
    ChartMoneyVariationEditMode.attach(this)
  }

  fun setData(data: List<MoneyVariationEntry>) {
    this.data.clear()
    this.data.addAll(data)

    minDataValue = data.minBy { it.amount }?.amount ?: 0.0
    maxDataValue = data.maxBy { it.amount }?.amount ?: 0.0

    requestLayout()
  }

  override fun getSuggestedMinimumHeight() = suggestionHeight

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
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
          y = getLocationAccordingDataValue(data.amount)
        )
      )
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
    val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

    setMeasuredDimension(
      measureDimension(desiredWidth, widthMeasureSpec),
      measureDimension(desiredHeight, heightMeasureSpec)
    )
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawCenterLine()
    canvas.drawVariationLines()
    canvas.drawCircleIndicators()
  }

  private fun Int.dp(): Float {
    return this * context.applicationContext.resources.displayMetrics.density
  }

  private fun drawData(draw: (data: MoneyVariationView) -> Unit) {
    currentYPosition = centerZeroY
    currentXPosition = 0.0F

    dataElements.forEach { data ->
      draw(data)
      currentXPosition += distanceBetweenElements
      currentYPosition = data.y
    }
  }

  private fun Canvas.drawVariationLines() = drawData { element ->
    if (element.y <= centerZeroY && currentYPosition <= centerZeroY) {
      drawMoneyVariationLine(positivePaint, element.y)
    }

    else if (element.y <= centerZeroY && currentYPosition > centerZeroY) {
      drawMoneyVariationLineWhenHitZero(negativePaint, positivePaint, element.y)
    }

    else if (element.y > centerZeroY && currentYPosition <= centerZeroY) {
      drawMoneyVariationLineWhenHitZero(positivePaint, negativePaint, element.y)
    }

    else if (element.y > centerZeroY && currentYPosition > centerZeroY) {
      drawMoneyVariationLine(negativePaint, element.y)
    }
  }

  private fun Canvas.drawCircleIndicators() = drawData { element ->
    if (element.data.amount >= 0) {
      drawCircleIndicator(positivePaint, element.y)
    } else {
      drawCircleIndicator(negativePaint, element.y)
    }
  }

  private fun Canvas.drawMoneyVariationLine(paint: Paint, finalY: Float) {
    drawLine(
      currentXPosition,
      currentYPosition,
      currentXPosition + distanceBetweenElements,
      finalY,
      paint
    )
  }

  private fun Canvas.drawMoneyVariationLineWhenHitZero(
    firstPaint: Paint,
    secondPaint: Paint,
    finalY: Float
  ) {
    var y = abs(currentYPosition - finalY)

    // Get points of triangle
    val pointA = PointF(0F, currentYPosition)
    val pointB = PointF(distanceBetweenElements, y)
    val pointC = PointF(0F, y)

    // Calculate distance between points.
    val a2 = lengthSquare(pointB, pointC)
    val b2 = lengthSquare(pointA, pointC)
    val c2 = lengthSquare(pointA, pointB)

    // length of sides be a, b, c
    val a = sqrt(a2)
    val b = sqrt(b2)
    val c = sqrt(c2)

    // From Cosine law
    var alpha = acos((b2 + c2 - a2) / (2 * b * c))
    var betta = acos((a2 + c2 - b2) / (2 * a * c))

    // Converting to degree
    alpha = (alpha * 180F / PI).toFloat()
    betta = (betta * 180F / PI).toFloat()

    y = finalY - centerZeroY
    val hype = y / sin(alpha)

    val centerHitX = sqrt(hype * hype - y * y)

    drawLine(
      currentXPosition,
      currentYPosition,
      currentXPosition + centerHitX,
      centerZeroY,
      firstPaint
    )

    drawLine(
      currentXPosition + centerHitX,
      centerZeroY,
      currentXPosition + distanceBetweenElements,
      finalY,
      secondPaint
    )
  }

  fun lengthSquare(p1: PointF, p2: PointF): Float {
    val xDiff: Float = p1.x - p2.x
    val yDiff: Float = p1.y - p2.y
    return xDiff * xDiff + yDiff * yDiff
  }

  private fun Canvas.drawCircleIndicator(paint: Paint, finalY: Float) {
    drawCircle(
      currentXPosition + distanceBetweenElements,
      finalY,
      circleMarkerRadius,
      paint
    )
  }

  private fun Canvas.drawCenterLine() {
    drawLine(
      0.toFloat(),
      centerZeroY,
      width.toFloat(),
      centerZeroY,
      centerPainter
    )
  }

  private fun defaultLinePaint(colorInt: Int) = Paint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    strokeWidth = variationLineSize
    color = ContextCompat.getColor(context, colorInt)
  }

  private fun updatePaintColor(paint: Paint, colorInt: Int) {
    paint.color = ContextCompat.getColor(context, colorInt)
    invalidate()
  }

  private fun getLocationAccordingDataValue(dataValue: Double): Float {
    val dataRange = maxDataValue - minDataValue
    return centerZeroY + ((dataValue * resolveHeight() / dataRange).toInt() * -1).toFloat()
  }

  private fun resolveHeight() = height - (circleMarkerRadius * 4)

  private fun computeCenterZeroY(): Float {
    // There are only positive values
    if (maxDataValue >= 0 && minDataValue >= 0) {
      return height.toFloat()
    }

    // There are only negative values
    if (maxDataValue < 0 && minDataValue < 0) {
      return 0F
    }

    // There are both values, so I need to find the center based on values
    val dataRange = abs(maxDataValue) + abs(minDataValue)
    return (maxDataValue * height / dataRange).toFloat()
  }
}

