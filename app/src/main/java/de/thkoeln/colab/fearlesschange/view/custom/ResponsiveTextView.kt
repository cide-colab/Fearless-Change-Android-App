package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.R
import kotlin.math.max


class ResponsiveTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    var charactersPerRow = 40
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    init {
        ellipsize = TextUtils.TruncateAt.END
        val a = context.obtainStyledAttributes(attrs, R.styleable.ResponsiveTextView, defStyleAttr, 0)
        charactersPerRow = a.getInt(R.styleable.ResponsiveTextView_charactersPerRow, charactersPerRow)
        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        if (w != oldw) {
            refitMeasurements(w, h)
//        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun refitMeasurements(textViewWidth: Int, h: Int? = null) {
        if (textViewWidth <= 0) return

        val textPaint = TextPaint()
        textPaint.textSize = 1f

        val ratio = 1f / textPaint.measureText("W")

        val characterWidth = (textViewWidth.toFloat() / charactersPerRow)
        val textSize = characterWidth * ratio

        this.textSize = textSize

        val padding = (characterWidth * 2).toInt()
        setPadding(padding, paddingTop, padding, paddingBottom)

        if (layoutParams.height != WRAP_CONTENT && h != null) {
            val height = (h.toFloat() - paddingTop - paddingBottom)
            maxLines = max((height / lineHeight).toInt(), 1)
        }
    }

    override fun onSaveInstanceState(): Parcelable? =
            InstanceState(super.onSaveInstanceState(), charactersPerRow, text.toString())

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        if (state is InstanceState) {
            text = state.text
            charactersPerRow = state.charactersPerRow
        }
    }

    private class InstanceState : BaseSavedState {

        var charactersPerRow: Int = 0
        var text: String = ""

        constructor(parentState: Parcelable?, charactersPerRow: Int = 0, text: String = "") : super(parentState) {
            init(charactersPerRow, text)
        }

        private constructor(input: Parcel) : super(input) {
            init(input.readInt(), input.readString() ?: "")
        }

        fun init(charactersPerRow: Int, text: String) {
            this.charactersPerRow = charactersPerRow
            this.text = text
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(text)
            out.writeInt(charactersPerRow)
        }

        companion object CREATOR : Parcelable.Creator<BaseSavedState> {
            override fun createFromParcel(input: Parcel): BaseSavedState {
                return InstanceState(input)
            }

            override fun newArray(size: Int): Array<BaseSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

}
