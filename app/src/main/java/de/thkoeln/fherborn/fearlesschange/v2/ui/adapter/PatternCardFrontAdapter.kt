package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.Pattern
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.getResourceId
import de.thkoeln.fherborn.fearlesschange.v2.ui.customs.card.PatternCardFront

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternCardFrontAdapter: SingleViewAdapter<Pattern, PatternCardFront>() {

    var onFavoriteClickedListener: ((Pattern?) -> Unit)? = null

    override fun onDataChange(view: PatternCardFront, data: Pattern?) {
        view.title = data?.title
        view.summary = data?.summary
        view.imageId = view.context.getResourceId(data?.pictureName, "drawable")
        view.favorite = data?.favorite
        view.onFavoriteClickedListener = { onFavoriteClickedListener?.invoke(data) }
    }

}