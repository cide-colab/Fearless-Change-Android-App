package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.toPx


class LabelChip @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    var color: Int = Color.argb(0, 0, 0, 0)
        set(value) {
            field = value
            setTextColor(field)
            paint.color = value
        }

    var name: String
        set(value) {
            text = value
        }
        get() = text.toString()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1.toPx().toFloat()
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LabelChip, defStyleAttr, 0)
        name = a.getString(R.styleable.LabelChip_label_name) ?: ""
        color = a.getColor(R.styleable.LabelChip_label_color, color)
        a.recycle()

        textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfHeight = h / 2f
        path.reset()
        path.addRoundRect(0f, 0f, w.toFloat(), h.toFloat(), (0 until 8).map { halfHeight }.toFloatArray(), Path.Direction.CW)
        path.close()
        setPadding(halfHeight.toInt(), 0, halfHeight.toInt(), 0)
    }

    override fun dispatchDraw(canvas: Canvas) {

//        val save = canvas.save()
//        canvas.clipPath(path)
//        canvas.drawColor(color)
        canvas.drawPath(path, paint)
        super.dispatchDraw(canvas)
//        canvas.restoreToCount(save)

    }

}
