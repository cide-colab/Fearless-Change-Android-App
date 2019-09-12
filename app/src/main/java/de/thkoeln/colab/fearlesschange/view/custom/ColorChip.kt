package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.ImageView
import androidx.core.view.setPadding
import com.google.android.flexbox.FlexboxLayout
import de.thkoeln.colab.fearlesschange.R
import kotlin.math.min

typealias OnColorChipCheckChangedListener = (chip: ColorChip) -> Unit

class ColorChipGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FlexboxLayout(context, attrs, defStyleAttr) {

    var onColorChipCheckChangedListener: OnColorChipCheckChangedListener = {}
    override fun addView(child: View) {
        super.addView(child)
        viewAdded(child)
    }

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        viewAdded(child)
    }

    override fun addView(child: View, width: Int, height: Int) {
        super.addView(child, width, height)
        viewAdded(child)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        super.addView(child, params)
        viewAdded(child)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        viewAdded(child)
    }

    //TODO remove if view removed

    private val colorChips = mutableListOf<ColorChip>()

    private fun viewAdded(child: View) {
        if (child is ColorChip) {
            colorChips.add(child)
            child.onCheckChangeListener = onCheckChanged
        }
    }

    fun getSelectedColor() = colorChips.find { it.isChecked }?.color

    private val onCheckChanged: (chip: ColorChip) -> Unit = { changed ->
        if (changed.isChecked) {
            colorChips.filterNot { it == changed }.forEach {
                val oldVal = it.isChecked
                it.isChecked = false
                if (oldVal) onColorChipCheckChangedListener(it)
            }
        }
        onColorChipCheckChangedListener(changed)
    }

}


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
