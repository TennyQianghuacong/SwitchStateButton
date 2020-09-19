package com.tenny.ssbutton.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.toColorInt
import androidx.core.graphics.toRectF
import com.tenny.ssbutton.utils.dp2px

/**
 * Created by TennyQ on 2020/9/19
 */
class RoundRectangleDrawable : Drawable(){

    private val defaultStrokeWith = 5.dp2px

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = defaultStrokeWith
    }

    val path = Path()

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        path.reset()

        val radius = (bounds.bottom - bounds.top) / 2f

        path.addRoundRect(bounds.toRectF(), radius, radius, Path.Direction.CCW)
    }

    fun setColor(color: Int){
        paint.color = color
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }

    override fun getOpacity(): Int {
        return when(paint.alpha) {
            0 -> PixelFormat.TRANSPARENT
            0xff -> PixelFormat.OPAQUE
            else -> PixelFormat.TRANSLUCENT
        }
    }

}