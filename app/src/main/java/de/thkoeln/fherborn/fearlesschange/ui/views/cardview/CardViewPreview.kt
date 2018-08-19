package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.getResourceId
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideApp
import kotlinx.android.synthetic.main.layout_card_view_preview.view.*


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewPreview : CardView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?): View = inflater.inflate(R.layout.layout_card_view_preview, rootView, false)

    var notesCount: Int = 0
        set(value) {
            field = value
            onNotesCountChanged(field)
        }

    private fun onNotesCountChanged(count: Int) {
        if (count > 0) {
            notes_count.text = count.toString()
            notes_count.visibility = VISIBLE
            notes_icon.visibility = VISIBLE
        } else {
            notes_count.visibility = GONE
            notes_icon.visibility = GONE
        }
    }

    override fun onCardChanged(card: Card?) {
        card?.let {
            card_title?.text = it.title
            card_problem?.text = it.problem
            fav_icon.setImageResource(
                    when {
                        card.favorite -> R.drawable.ic_favorite_full_white_24dp
                        else -> R.drawable.ic_favorite_white
                    }
            )

            GlideApp.with(context)
                    .load(getResourceId(context, card.pictureName, "drawable"))
                    .placeholder(R.drawable.img_placeholder)
                    .fitCenter()
                    .into(card_image)
        }
    }

}