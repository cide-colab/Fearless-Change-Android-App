package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardInfo
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.getResourceId
import de.thkoeln.fherborn.fearlesschange.v2.ui.customs.PatternCardPreviewView

/**
 * Created by florianherborn on 22.08.18.
 */
class CardPreviewAdapter: SingleViewAdapter<CardInfo, PatternCardPreviewView>() {

    var onCardClickedListener: ((CardInfo?) -> Unit)? = null

    override fun onDataChange(view: PatternCardPreviewView, data: CardInfo?) {
        view.title = data?.card?.title
        view.summary = data?.card?.summary
        view.imageId = view.context.getResourceId(data?.card?.pictureName, "drawable")
        view.favorite = data?.card?.favorite
        view.noteCount = data?.noteCount
        view.onCardClickedListener = { onCardClickedListener?.invoke(data) }
    }

}