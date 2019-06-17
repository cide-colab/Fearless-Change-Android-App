package de.thkoeln.colab.fearlesschange.ui.adapter

import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.helper.extensions.getResourceId
import de.thkoeln.colab.fearlesschange.ui.customs.card.PatternCardPreview

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternCardPreviewAdapter(var pattern: Pattern? = null): SingleViewAdapter<PatternInfo, PatternCardPreview>() {

    var onCardClickedListener: (PatternInfo?) -> Unit = {}

    override fun onDataChange(view: PatternCardPreview, data: PatternInfo?) {
        pattern = data?.pattern
        view.title = data?.pattern?.title
        view.summary = data?.pattern?.summary
        view.imageId = view.context.getResourceId(data?.pattern?.pictureName, "drawable")?: R.drawable.default_pattern_image
        view.favorite = data?.pattern?.favorite
        view.noteCount = data?.noteCount
        view.onCardClickedListener = { onCardClickedListener(data) }
    }

}