package com.spotify.voice.experience.view.listening

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.jellyfishanimation.R
import com.spotify.voice.experience.view.listening.CircleMath.Companion.degrees2rad
import com.spotify.voice.experience.view.listening.CircleMath.Companion.warpedRadius
import kotlin.math.cos
import kotlin.math.sin

class JellyFishView_Ashish : View {

    companion object {
        private const val DEFAULT_OFFSET_INCREMENT = 0.5
        private const val CIRCLE_DEGREES = 360
        private const val DEFAULT_PADDING = 0.5f
    }

    private lateinit var paint: Paint
    private var offset = -200.0
    var frequency = 2.0
    var phase = 1.0
    var amplitude = 0.0
    private var radius = 0.0
    private var centerX = 0.0
    private var centerY = 0.0
    var lineWidth = 10
    var color = R.color.jellyfish_blue
    var attenuation = 0.0
    var amplitude1 = dpToPx(this.context, 30).toFloat()
    private var path = Path()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        initialize()
    }

    private fun initialize() {
        setupPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        val height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        centerX = (width / 2).toDouble()
        centerY = (height / 2).toDouble()
        radius = dpToPx(context, 68).toDouble()

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setupPaint() {
        if (!::paint.isInitialized) {
            paint = Paint()
        }

        paint.color = ContextCompat.getColor(context, color)
        paint.isAntiAlias = true
        paint.strokeWidth = dpToPx(context, lineWidth).toFloat()
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.MITER
        paint.strokeCap = Paint.Cap.BUTT
    }

    override fun onDraw(canvas: Canvas?) {
        path.reset()
        paint.color = ContextCompat.getColor(context, color)
//        offset += DEFAULT_OFFSET_INCREMENT
//
//        val startingX = CircleMath.getX(centerX, radius, 0 - offset).toFloat()
//        val startingY = CircleMath.getY(centerY, radius, 0 - offset).toFloat()
//
//        path.moveTo(startingX, startingY)
//
//        // Log.d("Circles", "$centerX, $centerY, $radius, $attenuation, $frequency, $phase, $amplitude, $offset")
//        for (i in 0..CIRCLE_DEGREES) {
//            val newRadius =
//                warpedRadius(
//                    i.toDouble(),
//                    centerX,
//                    radius,
//                    attenuation,
//                    frequency,
//                    phase,
//                    amplitude
//                )
//            val xCoordinate = centerX + newRadius * cos(degrees2rad(i.toDouble() - offset))
//            val yCoordinate = centerY + newRadius * sin(degrees2rad(i.toDouble() - offset))
//            path.lineTo(xCoordinate.toFloat(), yCoordinate.toFloat())
//            path.moveTo(xCoordinate.toFloat(), yCoordinate.toFloat())
        // canvas?.drawPoint(X.toFloat(), Y.toFloat(), paint)
//        }
//        drawWarpedCircle()
//        canvas?.drawPath(path, paint)

        path.reset()
        paint.color = Color.parseColor("#1da6f9")
        path.moveTo(0f, height.toFloat())
        path.lineTo(0f, amplitude1)
        var i = 0
        while (i < width + 10) {
            val wx = i.toFloat()
            val wy = amplitude1 * 2 + amplitude1 * Math.sin((i + 10) * Math.PI / 350 + 0.3f).toFloat()
            path.lineTo(wx, wy)
            i += 10
        }
        path.lineTo(width.toFloat(), height.toFloat())
        path.close()


//         doneDrawing(true)

//        drawWarpedCircle() //        canvas?.drawPath(path, paint)
//        canvas?.drawPaint(paint)
//        drawWarpedCircle()
//        //c.drawLines(pts, p);
//        //c.drawLines(pts, p);
//        path.close()
//        paint.strokeWidth = 8.toFloat()
//        paint.pathEffect = null
//        paint.color = ContextCompat.getColor(context, color)
//        paint.style = Paint.Style.STROKE
//        canvas?.drawPath(path, paint)
    }


    fun redraw() {
        this.invalidate()
    }

    private fun dpToPx(context: Context, sizeInDp: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (sizeInDp * scale + DEFAULT_PADDING).toInt()
    }

    private fun drawWarpedCircle() {
        offset += 0.5
//        val startingX = CircleMath.getX(centerX, radius, 0 - offset).toFloat()
//        val startingY = CircleMath.getY(centerY, radius, 0 - offset).toFloat()
//        path.moveTo(startingX, startingY)
//        var count = 0
//        val xpoints = mutableListOf<Float>()
//        val ypoints = mutableListOf<Float>()
//        for (i in 0..13 step 4) {
//            if (count < 3) {
//                val newRadius = warpedRadius(
//                    degree = i.toDouble() ,
//                    centerX = centerX,
//                    radius = radius,
//                    attenuation = attenuation,
//                    frequency = frequency,
//                    phase = phase,
//                    amplitude = amplitude
//                );
//                val x = (centerX + newRadius * cos(degrees2rad(i*30.0)))
//                val y = (centerY + newRadius * sin(degrees2rad(i*30.0)))
//                xpoints.add(count, x.toFloat())
//                ypoints.add(count,y.toFloat())
//                count += 1
//            }
//            else{
//                count = 0
//                path.cubicTo(xpoints[0], ypoints[0], xpoints[1], ypoints[1], xpoints[2], ypoints[2])
//            }
//        }
        // return path
    }
}
