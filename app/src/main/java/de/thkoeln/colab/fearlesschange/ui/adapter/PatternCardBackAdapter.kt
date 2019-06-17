package de.thkoeln.colab.fearlesschange.ui.adapter

import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.ui.customs.card.PatternCardBack

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternCardBackAdapter: SingleViewAdapter<Pattern, PatternCardBack>() {

    var onFavoriteClickedListener: () -> Unit = {}

    override fun onDataChange(view: PatternCardBack, data: Pattern?) {
        view.title = data?.title
        view.problem = data?.problem
        view.solution = data?.solution
        view.favorite = data?.favorite
        view.onFavoriteClickedListener = { onFavoriteClickedListener() }
    }

}