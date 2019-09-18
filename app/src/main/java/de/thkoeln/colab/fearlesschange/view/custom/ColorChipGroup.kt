package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayout

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