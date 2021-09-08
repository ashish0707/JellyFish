package com.example.jellyfishanimation

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import com.spotify.voice.experience.view.listening.CircleMath
import com.spotify.voice.experience.view.listening.CircleMath.Companion.degrees2rad
import kotlin.math.cos
import kotlin.math.sin
import android.view.animation.LinearInterpolator

import android.view.animation.Animation

import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat


class WaveViewBlue @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var amplitude = 10f.toDp() // scale
    private var speed = 0f
    private val path = Path()
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animator: ValueAnimator? = null
    val rotate : RotateAnimation? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        animator?.cancel()
        animator = createAnimator().apply { start() }
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 10000
        rotate.interpolator = LinearInterpolator()
        rotate.repeatCount = Animation.INFINITE;
        paint.color = ContextCompat.getColor(this.context, R.color.jellyfish_blue)
        startAnimation(rotate)
    }

    override fun onDraw(c: Canvas) {
        c.drawPath(drawWrappedCircle(180), paint)
    }

    private fun createAnimator(): ValueAnimator {
        return ValueAnimator.ofFloat(0f, Float.MAX_VALUE).apply {
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                speed -= WAVE_SPEED
                createPath()
                invalidate()

            }
        }
    }

    private fun createPath() {
        path.reset()
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.MITER
        paint.strokeCap = Paint.Cap.BUTT
//        path.moveTo(40f, height.toFloat() / 2)
//        var i = 40
//        var wx = 0f
//        var wy = 0f
//        while (i < width - 30) {
//            wx = i.toFloat()
//            wy =
//                amplitude * 2 + amplitude * Math.cos((i + 40) * Math.PI / WAVE_AMOUNT_ON_SCREEN + speed)
//                    .toFloat()
//            path.lineTo(wx, wy)
//            i += 10
//        }

//        path.moveTo();
//        path.lineTo(width.toFloat() - 40f, height.toFloat() / 2)
//        path.addArc(40f, 40f, width.toFloat() - 40f, height.toFloat() - 40f, 0f, 180f);


    }

    private fun drawWrappedCircle(offset: Int): Path {
        val path = Path()
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        var radius = 180.0f
        var newRadius = 0.0f

        val startingX = CircleMath.getX(centerX, radius, 0.0f)
        val startingY = CircleMath.getY(centerY, radius, 0.0f)
        path.moveTo(startingX, startingY)
        var xCoordinate = 0.0f
        var yCoordinate = 0.0f

//        amplitude = dpToPx(this.context, Random.nextInt(15,20).toFloat())
        for (i in 0..360) {
            if(i in offset..offset +180){
                newRadius = radius + amplitude * sin((i) * Math.PI / WAVE_AMOUNT_ON_SCREEN + speed)
                    .toFloat()
                 xCoordinate = centerX + radius * cos(degrees2rad(i.toFloat()))
                 yCoordinate = centerY + newRadius * sin(degrees2rad(i.toFloat()))
            }
            else{
                 xCoordinate = centerX + radius * cos(degrees2rad(i.toFloat()))
                 yCoordinate = centerY + radius * sin(degrees2rad(i.toFloat()))
            }

            path.lineTo(xCoordinate, yCoordinate)
//            println("Radius x y : $newRadius $xCoordinate $yCoordinate")
        }
        return path
    }

    private fun dpToPx(context: Context?, dp: Float): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }

    companion object {
        const val WAVE_SPEED = 0.050f
        const val WAVE_AMOUNT_ON_SCREEN = 70
    }

    private fun Float.toDp() = this * context.resources.displayMetrics.density
}