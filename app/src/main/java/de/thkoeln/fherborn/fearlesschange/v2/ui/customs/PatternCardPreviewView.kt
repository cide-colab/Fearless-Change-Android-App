package de.thkoeln.fherborn.fearlesschange.v2.ui.customs

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedImage
import de.thkoeln.fherborn.fearlesschange.v2.helper.view.ViewAttribute
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.AdaptableView
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.SingleViewAdapter
import kotlinx.android.synthetic.main.card_preview.view.*

/**
 * Created by Florian on 23.08.2018.
 */
class PatternCardPreviewView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr), AdaptableView<PatternCardPreviewView> {


    private val defaultFavorite = false
    var favorite by ViewAttribute(defaultFavorite) {
        card_preview_favorite_icon.setImageResource(when (it) {
            true -> R.drawable.ic_favorite_full_white
            else -> R.drawable.ic_favorite_white
        })
    }
    var title by ViewAttribute("") {
        card_preview_card_title.text = it
    }
    var summary by ViewAttribute("") {
        card_preview_card_summary.text = it
    }
    private val defaultImageId = R.drawable.default_pattern_image
    var imageId by ViewAttribute(defaultImageId) {
        card_preview_card_image.setOptimizedImage(it)
    }
    private val defaultNoteCount = 0
    var noteCount by ViewAttribute(defaultNoteCount) {
        when (it) {
            0 -> {
                card_preview_notes_count.visibility = View.INVISIBLE
                card_preview_notes_icon.visibility = View.INVISIBLE
            }
            else -> {
                card_preview_notes_count.text = it.toString()
                card_preview_notes_count.visibility = View.VISIBLE
                card_preview_notes_icon.visibility = View.VISIBLE
            }
        }
    }

    var onCardClickedListener: (() -> Unit)? = null
        set(value) {
            field = value
            card_preview_card.setOnClickListener { value?.invoke() }
        }

    init {
        View.inflate(context, R.layout.card_preview, this)
        context?.obtainStyledAttributes(attrs, R.styleable.PatternCardPreviewView, defStyleAttr, 0)?.apply {
            try {
                favorite = getBoolean(R.styleable.PatternCardPreviewView_PatternCardFavorite, defaultFavorite)
                title = getString(R.styleable.PatternCardPreviewView_PatternCardTitle)
                summary = getString(R.styleable.PatternCardPreviewView_PatternCardSummary)
                imageId = getInt(R.styleable.PatternCardPreviewView_PatternCardImage, defaultImageId)
                noteCount = getInt(R.styleable.PatternCardPreviewView_PatternCardNoteCount, defaultNoteCount)
            } finally {
                recycle()
            }
        }
    }

    override fun <T> setAdapter(adapter: SingleViewAdapter<T, PatternCardPreviewView>) {
        adapter.registerView(this)
    }
}