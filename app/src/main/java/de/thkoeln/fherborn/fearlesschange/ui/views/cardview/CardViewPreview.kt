package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.card_preview.view.*
import java.util.regex.Pattern


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewPreview : CardView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?): View = inflater.inflate(R.layout.card_preview, rootView, false)

    var notesCount: Int = 0
        set(value) {
            field = value
            onNotesCountChanged(field)
        }

    private fun onNotesCountChanged(count: Int) {
        if (count > 0) {
            card_preview_notes_count.text = count.toString()
            card_preview_notes_count.visibility = VISIBLE
            card_preview_notes_icon.visibility = VISIBLE
        } else {
            card_preview_notes_count.visibility = GONE
            card_preview_notes_icon.visibility = GONE
        }
    }
    override fun onCardChanged(card: de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.Pattern?) {
        
    }

}