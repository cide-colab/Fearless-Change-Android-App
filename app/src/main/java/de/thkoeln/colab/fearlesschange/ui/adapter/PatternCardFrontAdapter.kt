package de.thkoeln.colab.fearlesschange.ui.adapter

import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.helper.extensions.getResourceId
import de.thkoeln.colab.fearlesschange.ui.customs.card.PatternCardFront

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternCardFrontAdapter: SingleViewAdapter<Pattern, PatternCardFront>() {

    var onFavoriteClickedListener: () -> Unit = {}

    override fun onDataChange(view: PatternCardFront, data: Pattern?) {
        view.title = data?.title
        view.summary = data?.summary
        view.imageId = view.context.getResourceId(data?.pictureName, "drawable")?: R.drawable.default_pattern_image
        view.favorite = data?.favorite
        view.onFavoriteClickedListener = { onFavoriteClickedListener }
    }

}