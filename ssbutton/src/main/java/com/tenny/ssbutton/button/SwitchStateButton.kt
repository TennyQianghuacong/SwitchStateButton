package com.tenny.ssbutton.button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.tenny.ssbutton.R
import com.tenny.ssbutton.drawable.RoundRectangleDrawable
import com.tenny.ssbutton.utils.dp2px
import com.tenny.ssbutton.utils.goldDivider

/**
 * Created by TennyQ on 2020/9/19
 */
class SwitchStateButton(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val defaultTextSize = 16.dp2px
    private val defaultHeight = 42.dp2px.toInt()
    private val defaultWith = 200.dp2px.toInt()

    private val baseTextGapSpace: Int

    private val defaultColor = "#F9A825".toColorInt()

    private var outerBoundsColor: Int
    private var innerBoundsColor: Int
    private var textSize: Float
    private var elementContent : List<String>
    private var elementCount: Int
    private var defaultIndex: Int

    private var textOffsetX: FloatArray
    private var textOffsetY: Float = 0f

    private val outDrawable = RoundRectangleDrawable()
    private val innerDrawable = RoundRectangleDrawable()

    private val fontMetrics = Paint.FontMetrics()
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchStateButton)

        outerBoundsColor = typeArray.getColor(R.styleable.SwitchStateButton_ssb_backGroundColor, defaultColor)
        innerBoundsColor = typeArray.getColor(R.styleable.SwitchStateButton_ssb_innerColor, defaultColor)
        textSize = typeArray.getDimension(R.styleable.SwitchStateButton_ssb_testSize, defaultTextSize)
        elementContent = typeArray.getString(R.styleable.SwitchStateButton_ssb_strings).toString().split(",")
        defaultIndex = typeArray.getIndex(R.styleable.SwitchStateButton_ssb_defaultIndex)
        elementCount = elementContent.size

        typeArray.recycle()

        baseTextGapSpace = (textSize / goldDivider).toInt()
        textPaint.textSize = textSize
        outDrawable.setColor(outerBoundsColor)
        innerDrawable.setColor(innerBoundsColor)
        textOffsetX = FloatArray(elementCount)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureText()
    }

    /**
     * measure text size
     */
    private fun measureText() {
        var height = baseTextGapSpace * 2 + textSize
        var width = 0f

        for ((index, text) in elementContent.withIndex()){
            width += baseTextGapSpace

            textOffsetX[index] = width
            width += textPaint.measureText(text)

            width += baseTextGapSpace
        }
        textPaint.getFontMetrics(fontMetrics)
        textOffsetY = (height - (fontMetrics.ascent + fontMetrics.descent)) /2

        setMeasuredDimension(width.toInt(), height.toInt())
    }

    /**
     * when size changed, reset drawable's bound
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        outDrawable.setBounds(0, 0, w, h)
        innerDrawable.setBounds(0, 0, w / 2, h)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawDrawable(canvas)
        drawText(canvas)
    }

    /**
     * draw inner drawable and outer drawable
     */
    private fun drawDrawable(canvas: Canvas){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            canvas.save()
            canvas.clipOutPath(innerDrawable.path)
            outDrawable.draw(canvas)
            canvas.restore()
        } else {
            outDrawable.draw(canvas)
        }

        innerDrawable.draw(canvas)
    }

    /**
     * draw text
     */
    private fun drawText(canvas: Canvas){
        for ((index, text) in elementContent.withIndex()){
            canvas.drawText(text, textOffsetX[index], textOffsetY, textPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked) {

        }
        return super.onTouchEvent(event)
    }

}