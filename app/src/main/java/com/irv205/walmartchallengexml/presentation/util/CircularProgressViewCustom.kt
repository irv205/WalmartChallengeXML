package com.irv205.walmartchallengexml.presentation.util

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.irv205.walmartchallengexml.R

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Default values for custom attributes
    private var progressColor: Int = ContextCompat.getColor(context, R.color.loading)
    private var strokeWidth: Float = 10f
    private var progress: Float = 0f

    // Paint object to define how the circular progress will be drawn
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    // RectF object for defining the bounds of the circle
    private val rect = RectF()

    private var animator: ValueAnimator? = null

    init {
        // Load custom attributes from XML
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircularProgressView,
            0, 0
        ).apply {
            try {
                // Set progress color and stroke width from XML attributes
                progressColor = getColor(R.styleable.CircularProgressView_progressColor, progressColor)
                strokeWidth = getDimension(R.styleable.CircularProgressView_strokeWidth, strokeWidth)
            } finally {
                recycle()
            }
        }

        // Set the paint properties
        paint.color = progressColor
        paint.strokeWidth = strokeWidth

        // Start the animation
        startAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate the circle's bounds
        val padding = strokeWidth / 2
        rect.set(padding, padding, width - padding, height - padding)

        // Draw the progress circle
        canvas.drawArc(rect, -90f, progress, false, paint)
    }

    // Function to start animating the progress in a loop
    fun startAnimation() {
        animator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 2000 // Duration of the animation in milliseconds
            repeatCount = ValueAnimator.INFINITE
            interpolator = null // You can set an interpolator if needed
            addUpdateListener {
                progress = it.animatedValue as Float
                invalidate() // Redraw the view on every animation frame
            }
            start()
        }
    }

    // Function to stop the animation
    fun stopAnimation() {
        animator?.cancel()
    }

    // Clean up the animator when the view is detached
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    // Custom method to update progress color dynamically
    fun setProgressColor(color: Int) {
        progressColor = color
        paint.color = progressColor
        invalidate() // Redraw the view with the new color
    }

    // Custom method to update stroke width dynamically
    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        paint.strokeWidth = strokeWidth
        invalidate() // Redraw the view with the new stroke width
    }
}