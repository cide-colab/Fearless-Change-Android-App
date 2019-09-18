package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import de.thkoeln.colab.fearlesschange.R
import kotlinx.android.synthetic.main.badge.view.*

class Badge : ConstraintLayout {

    var icon: Drawable? = null
        set(value) {
            field = value
            label_icon.setImageDrawable(field)
        }

    var text: String? = null
        set(value) {
            field = value
            label_text.text = field
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.badge, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.Badge, defStyle, 0)

        text = a.getString(R.styleable.Badge_badgeText)

        if (a.hasValue(R.styleable.Badge_badgeIcon)) {
            icon = a.getDrawable(R.styleable.Badge_badgeIcon)
        }
        a.recycle()
    }
}
