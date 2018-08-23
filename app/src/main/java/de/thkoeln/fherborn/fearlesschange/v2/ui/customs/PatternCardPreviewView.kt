package de.thkoeln.fherborn.fearlesschange.v2.ui.customs

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.view.ViewBindings
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.AdaptableView
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.SingleViewAdapter

/**
 * Created by Florian on 23.08.2018.
 */
class PatternCardPreviewView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr), AdaptableView<PatternCardPreviewView> {

    private var bindings = ViewBindings(this)
    var title by bindings.textFor(R.id.card_preview_card_title)
    var summary by bindings.textFor(R.id.card_preview_card_summary)
    var imageId by bindings.imageFor(R.id.card_preview_card_image, R.drawable.default_pattern_image)
    var favorite by bindings.booleanImageSwitchFor(
            R.id.card_preview_favorite_icon,
            R.drawable.ic_favorite_full_white,
            R.drawable.ic_favorite_white
    )
    var noteCount by bindings.textVisibilitySwitchFor(
            R.id.card_preview_notes_count,
            R.id.card_preview_notes_icon,
            default = 0
    ) { it?.let { value -> value > 0 } ?: false }
    var onCardClickedListener by bindings.onClickListenerFor(R.id.card_preview_card)

    init {
        View.inflate(context, R.layout.card_preview, this)
        context?.obtainStyledAttributes(attrs, R.styleable.PatternCardPreviewView, defStyleAttr, 0)?.apply {
            try {
                favorite = getBoolean(R.styleable.PatternCardPreviewView_PatternCardFavorite, false)
                title = getString(R.styleable.PatternCardPreviewView_PatternCardTitle)
                summary = getString(R.styleable.PatternCardPreviewView_PatternCardSummary)
                imageId = getInt(R.styleable.PatternCardPreviewView_PatternCardImage, R.drawable.default_pattern_image)
                noteCount = getInt(R.styleable.PatternCardPreviewView_PatternCardNoteCount, 0)
            } finally {
                recycle()
            }
        }
    }

    override fun <T> setAdapter(adapter: SingleViewAdapter<T, PatternCardPreviewView>) {
        adapter.registerView(this)
    }
}