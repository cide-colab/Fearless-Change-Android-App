package de.thkoeln.colab.fearlesschange.ui.customs.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.ui.adapter.AdaptableView
import de.thkoeln.colab.fearlesschange.ui.adapter.SingleViewAdapter

/**
 * Created by Florian on 23.08.2018.
 */
class PatternCardPreview @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), AdaptableView<PatternCardPreview> {

    private val delegation = PatternCardDelegation(this)
    var title by delegation.updateTitle(R.id.card_preview_card_title)
    var summary by delegation.updateSummary(R.id.card_preview_card_summary)
    var imageId by delegation.updateCardImage(R.id.card_preview_card_image)
    var favorite by delegation.updateFavoriteIcon(R.id.card_preview_favorite_icon)
    var noteCount by delegation.updateNotesCount(R.id.card_preview_notes_count, R.id.card_preview_notes_icon)
    var background by delegation.updateCardBackground(R.id.card_preview_wrapper)
    var contentBackground by delegation.updateCardContentBackground(R.id.card_preview_content)
    var onCardClickedListener by delegation.updateClickListener(R.id.card_preview_card)

    init {
        View.inflate(context, R.layout.pattern_card_preview, this)
        context?.obtainStyledAttributes(attrs, R.styleable.PatternCardPreview, defStyleAttr, 0)?.apply {
            try {
                favorite = getBoolean(R.styleable.PatternCardPreview_PatternCardFavorite, delegation.favoriteDefault)
                title = getString(R.styleable.PatternCardPreview_PatternCardTitle) ?: delegation.titleDefault
                summary = getString(R.styleable.PatternCardPreview_PatternCardSummary) ?: delegation.summaryDefault
                imageId = getInt(R.styleable.PatternCardPreview_PatternCardImage, delegation.imageDefault)
                noteCount = getInt(R.styleable.PatternCardPreview_PatternCardNoteCount, delegation.noteCountDefault)
                background = getInt(R.styleable.PatternCardPreview_PatternCardBackground, delegation.backgroundDefault)
                contentBackground = getInt(R.styleable.PatternCardPreview_PatternCardContentBackground, delegation.contentBackgroundDefault)
            } finally {
                recycle()
            }
        }
    }

    override fun <T> setAdapter(adapter: SingleViewAdapter<T, PatternCardPreview>) {
        adapter.registerView(this)
    }
}