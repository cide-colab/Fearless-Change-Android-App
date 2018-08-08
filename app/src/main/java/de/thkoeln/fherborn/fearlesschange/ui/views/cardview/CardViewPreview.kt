package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.util.AttributeSet
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.getResourceId
import kotlinx.android.synthetic.main.layout_card_view_preview.view.*


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewPreview : CardView {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?)
            = inflater.inflate(R.layout.layout_card_view_preview, rootView, false)

    override fun onCardChanged(card: Card?) {
        card?.let {
            card_title?.text = it.title
            card_problem?.text = it.problem
            card_image.setImageResource(
                    getResourceId(context, card.pictureName, "drawable", context.packageName)
                            ?:R.drawable.img_placeholder)
        }
    }

}