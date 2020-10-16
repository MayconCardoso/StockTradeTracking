package com.mctech.stocktradetracking.library.chart.utils

import android.graphics.PointF
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.math.tan

/**
 *
 */
internal fun lengthSquare(p1: PointF, p2: PointF): Float {
  val xDiff: Float = p1.x - p2.x
  val yDiff: Float = p1.y - p2.y
  return xDiff * xDiff + yDiff * yDiff
}

/**
 * @param pointA first point that assemble a triangle
 * @param pointB second point that assemble a triangle
 * @param pointC third point that assemble a triangle
 * @param currentXPosition current X position of chart
 * @param intersectionHeight the intersection height with x axe
 * @param angleFunction function that will compute the angle projection.
 */
internal fun computeCenterProjectionRatio(
  pointA : PointF,
  pointB : PointF,
  pointC : PointF,
  currentXPosition : Float,
  intersectionHeight : Float,
  angleFunction : (Float) -> Float
) : Float {
  // Calculate distance between points.
  val a2 = lengthSquare(pointB, pointC)
  val b2 = lengthSquare(pointA, pointC)
  val c2 = lengthSquare(pointA, pointB)

  // length of sides be a, b, c
  val b = sqrt(b2)
  val c = sqrt(c2)

  // From Cosine law
  var angle = angleFunction((b2 + c2 - a2) / (2 * b * c))

  // Converting to degree
  angle = Math.toRadians((angle * 180F / PI)).toFloat()

  // Compute center X projection.
  val projection = tan(angle)

  val tangent = projection * intersectionHeight

  // Compute center X projection.
  return currentXPosition + tangent
}



