package com.example.jellyfishanimation

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import com.spotify.voice.experience.view.listening.CircleMath
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

class JellyFishView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_OFFSET_INCREMENT = 0.5f
        private const val CIRCLE_DEGREES = 360
        private const val DEFAULT_ATTENUATION_POWER_EIGHT = 8
        private const val DEFAULT_ATTENUATION_POWER_THIRTY_TWO = 32
    }

    enum class state {
        noState,
        transitionToListening,
        listening,
        transitionToThinking,
        thinking,
        transitionToNone
    }

    private var phase = 1.0f
    var currentState: state = state.noState
    private var paint = Paint()
    var currentAmplitude = 0.0f
    var offset = -200f
    var attenuation = 0.0f
    private var speed = 0.03f

    private var listeningMaxAmplitude: Float = 0.3f
    private var listeningMinAmplitude: Float = 0.1f
    private var thinkingAmplitude: Float = 0.2f
    private var thinkingFrequency: Float = 6f

    private var frequency = 25f

    private var nonWarpCircumference = 207.0f
    private var animator: ValueAnimator? = null

    enum class CircleColor(val id: Int) {
        BLUE(R.color.jellyfish_blue),
        NEON(R.color.jellyfish_neon),
        TEAL(R.color.jellyfish_teal)
    }

    data class circle(val att: Float, val lineWidth: Int, val color: CircleColor)

    val circles = listOf(
        circle(-100f, 4, CircleColor.BLUE),
        circle(-1.8f, 8, CircleColor.TEAL),
        circle(-1.4f, 8, CircleColor.NEON)
    )

    init {
        setUpPaint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        animator?.cancel()
        animator = createAnimator().apply { start() }
    }

    private fun createAnimator(): ValueAnimator {
        return ValueAnimator.ofFloat(0f, Float.MAX_VALUE).apply {
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                currentState = state.transitionToListening
                wrapCircle()
            }
        }
    }

    private fun wrapCircle() {
        phase = ((phase + Math.PI * speed).rem(2 * Math.PI)).toFloat()
        if (currentState == state.transitionToListening) {
            currentAmplitude += 0.01f
            if (currentAmplitude >= listeningMaxAmplitude) {
                currentAmplitude = listeningMaxAmplitude
                currentState = state.listening
            }
        } else if (currentState == state.transitionToNone) {
            currentAmplitude -= 0.01f
            if (currentAmplitude <= 0.001f){
                currentAmplitude = 0f
                currentState = state.noState
                        invalidate()
            }
        } else if (currentState == state.transitionToThinking && currentAmplitude < thinkingAmplitude) {
            currentAmplitude += 0.02f
            if (frequency <= thinkingFrequency) {
                frequency += 0.2f
            }
            if (currentAmplitude == thinkingAmplitude) {
                currentState = state.thinking
            }
        } else if (currentState == state.transitionToThinking && currentAmplitude > thinkingAmplitude) {
            currentAmplitude -= 0.02f
            if (frequency <= thinkingFrequency) {
                frequency += 0.2f
            }
            if (currentAmplitude == thinkingAmplitude) {
                currentState = state.thinking
            }
        }
        if (currentState == state.listening) {
//            currentAmplitude =
//                CircleMath.relativeSoundLevel(Random.nextInt(-120, -100).toFloat()) * 10 + 0.1f
            currentAmplitude = 0.6f * Random.nextDouble(0.6).toFloat() + 0.1f
            frequency = 2.0f
            if (currentAmplitude >= 0.5f) {
                currentAmplitude = 0.5f
            }
            if (currentAmplitude <= 0.1f) {
                currentAmplitude = 0.1f
            }
            if (frequency >= 20) {
                frequency = 20.0f
            }

            invalidate()
        }
    }

    private fun drawWrappedCircle(): Path {
        val path = Path()
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        val radius = dpToPx(this.context, 68.0f)
        offset += 0.5f
        val startingX = CircleMath.getX(centerX, radius, 0 - offset)
        val startingY = CircleMath.getY(centerY, radius, 0 - offset)
        path.moveTo(startingX, startingY)
        for (i in 0..CIRCLE_DEGREES) {
            val newRadius = warpedRadius(
                i.toFloat(),
                centerX,
                radius,
                attenuation,
                frequency,
                phase,
                currentAmplitude
            )
            val xCoordinate = centerX + newRadius * cos(degrees2rad(i.toFloat() - offset))
            val yCoordinate = centerY + newRadius * sin(degrees2rad(i.toFloat() - offset))
            path.lineTo(xCoordinate, yCoordinate)
//            println("Radius x y : $newRadius $xCoordinate $yCoordinate")
        }
        return path
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        for (i in circles) {
            attenuation = i.att
            paint.strokeWidth = i.lineWidth.toFloat()
            paint.color = ContextCompat.getColor(context, i.color.id)
            canvas.drawPath(drawWrappedCircle(), paint)
        }
    }

    private fun setUpPaint() {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.MITER
        paint.strokeCap = Paint.Cap.BUTT
    }

    private fun attenuateSineWave(x: Float): Float {
        return (1 / (1 + x.pow(DEFAULT_ATTENUATION_POWER_EIGHT))).pow(
            DEFAULT_ATTENUATION_POWER_THIRTY_TWO
        )
    }

    private fun pathAdjustment(
        x: Float,
        attenuation: Float,
        frequency: Float,
        phase: Float
    ): Float {
        val dampening = attenuateSineWave(x)
        return dampening * sin(frequency * x - phase) / attenuation
    }

    private fun warpedRadius(
        degree: Float,
        centerX: Float,
        radius: Float,
        attenuation: Float,
        frequency: Float,
        phase: Float,
        amplitude: Float
    ): Float {
        val x = (degree - nonWarpCircumference) / radius
        val f = pathAdjustment(x, attenuation, frequency, phase)
        return f * radius * amplitude + radius
    }

    //((8 + (8/((d - cx)/r)^8))^2) * sin(f * ((d-cx)/r) - p) / a

    private fun degrees2rad(number: Float): Float {
        return (number * Math.PI / 180).toFloat()
    }

    private fun getX(centerX: Float, radius: Float, degree: Float): Float {
        return centerX + radius * cos(degrees2rad(degree))
    }

    private fun getY(centerY: Float, radius: Float, degree: Float): Float {
        return centerY + radius * sin(degrees2rad(degree))
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
}