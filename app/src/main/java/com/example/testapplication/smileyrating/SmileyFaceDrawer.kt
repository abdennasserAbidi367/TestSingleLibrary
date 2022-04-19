package com.example.testapplication.smileyrating

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint.*
import android.graphics.RectF
import android.util.AttributeSet
import com.example.testapplication.R

/**
 * Created by Yugansh on 14/07/20.
 */
internal class SmileyFaceDrawer(context: Context, attributeSet: AttributeSet?) {

    val cont = context

    private val config: SmileyViewConfig = SmileyViewConfig(context, attributeSet)
    private val animator = SmileyAnimator(config)

    fun drawFace(canvas: Canvas) {
        config.paint.color = config.faceColor
        config.paint.isAntiAlias = true
        config.paint.style = Style.FILL
        canvas.drawArc(config.faceBgRect, 0f, 180f, true, config.paint)

        drawEyes(canvas, SmileyState.of(config.defaultRating))
        drawFaceForState(canvas, SmileyState.of(config.defaultRating))
    }

    fun onMeasure(width: Int, height: Int) {
        config.onMeasure(width, height)
        animator.updateEyesValues()
    }

    fun updateRating(rating: Float, invalidate: () -> Unit) {
        config.defaultRating = rating.toInt()
        animator.animateEyesToNewPos(invalidate)
    }

    private fun drawFaceForState(canvas: Canvas, state: SmileyState) {
        //update paint
        config.paint.color = config.mouthColor
        config.paint.style = Style.STROKE
        when (state) {
            SmileyState.Sad -> drawVerySadFace(canvas, state)
            SmileyState.Neutral -> drawSadFace(canvas)
            SmileyState.Okay -> drawNeutralFace(canvas)
            SmileyState.Happy -> drawHappyFace(canvas, state)
            SmileyState.Amazing -> drawAmazingFace(canvas)
        }
    }

    private fun drawEyes(canvas: Canvas, state: SmileyState) {
        if (state == SmileyState.Sad) {
            config.paint.color = config.eyesColor
            config.paint.style = Style.STROKE
            val margin =  100
            val rectangle = RectF(config.currEyeLX.toFloat() - margin, config.currEyeY.toFloat() - margin, config.currEyeLX.toFloat() + margin, config.currEyeY.toFloat() + margin)
            canvas.drawArc(rectangle, 0f, 180f, false, config.paint)

            val rectangleRight = RectF(config.currEyeRX.toFloat() - margin, config.currEyeY.toFloat() - margin, config.currEyeRX.toFloat() + margin, config.currEyeY.toFloat() + margin)
            canvas.drawArc(rectangleRight, 0f, 180f, false, config.paint)
        } else {
            config.paint.color = config.eyesColor
            config.paint.style = Style.FILL
            canvas.drawCircle(
                config.currEyeLX.toFloat(),
                config.currEyeY.toFloat(),
                config.eyeRadius,
                config.paint
            )
            canvas.drawCircle(
                config.currEyeRX.toFloat(),
                config.currEyeY.toFloat(),
                config.eyeRadius,
                config.paint
            )
        }
    }

    private fun drawSadFace(canvas: Canvas) {
        canvas.drawArc(config.sadFaceRect, 0f, -180f, false, config.paint)
    }

    private fun drawNeutralFace(canvas: Canvas) {
        canvas.drawLine(
            config.neutralFaceRect.left,
            config.neutralFaceRect.top,
            config.neutralFaceRect.right,
            config.neutralFaceRect.bottom,
            config.paint
        )
    }

    private fun drawHappyFace(canvas: Canvas, state: SmileyState) {
        val rect = if (state is SmileyState.Okay) config.okayFaceRect else config.happyFaceRect
        canvas.drawArc(rect, 0f, 180f, false, config.paint)
    }

    private fun drawVerySadFace(canvas: Canvas, state: SmileyState) {
        canvas.drawArc(config.sadFaceRect, 0f, -180f, false, config.paint)
    }

    private fun drawAmazingFace(canvas: Canvas) {
        //Draw mouth
        config.paint.style = Style.FILL
        config.paint.color = config.mouthColor
        canvas.drawArc(config.amazingFaceRect, 0f, 180f, true, config.paint)

        //Draw tongue
        config.paint.color = config.tongueColor
        canvas.drawArc(config.tongueRect, 0f, 180f, false, config.paint)
    }
}