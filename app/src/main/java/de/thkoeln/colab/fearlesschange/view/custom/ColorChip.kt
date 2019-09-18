package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.ImageView
import androidx.core.view.setPadding
import de.thkoeln.colab.fearlesschange.R
import kotlin.math.min

typealias OnColorChipCheckChangedListener = (chip: ColorChip) -> Unit

class ColorChip @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr), Checkable {

    private val d = resources.getDrawable(R.drawable.ic_check_black_24dp, null)

    var onCheckChangeListener: OnColorChipCheckChangedListener = {}

    private var isChecked = false

    override fun isChecked() = isChecked

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun setChecked(checked: Boolean) {
        isChecked = checked
        onCheckChangeListener(this)
        if (isChecked) {
            setImageDrawable(d)
        } else {
            setImageDrawable(null)
        }
    }

    var color: Int = Color.argb(0, 0, 0, 0)
        set(value) {
            field = value
            paint.color = value
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ColorChip, defStyleAttr, 0)
        color = a.getColor(R.styleable.ColorChip_color, color)
        a.recycle()

        isClickable = true
        isFocusable = true

    }


    override fun performClick(): Boolean {
        super.performClick()
        toggle()
        return true
    }

    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfHeight = h / 2f
        val halfWidth = w / 2f
        setPadding(w / 4)
        path.reset()
        path.addCircle(halfWidth, halfHeight, min(halfWidth, halfHeight), Path.Direction.CW)
        path.close()

    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
        super.onDraw(canvas)

    }

}
