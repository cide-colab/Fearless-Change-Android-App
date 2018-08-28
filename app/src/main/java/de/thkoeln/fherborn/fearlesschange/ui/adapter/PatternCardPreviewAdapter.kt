package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.getResourceId
import de.thkoeln.fherborn.fearlesschange.v2.ui.customs.card.PatternCardPreview

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternCardPreviewAdapter: SingleViewAdapter<PatternInfo, PatternCardPreview>() {

    var onCardClickedListener: ((PatternInfo?) -> Unit)? = null

    override fun onDataChange(view: PatternCardPreview, data: PatternInfo?) {
        view.title = data?.pattern?.title
        view.summary = data?.pattern?.summary
        view.imageId = view.context.getResourceId(data?.pattern?.pictureName, "drawable")?: R.drawable.default_pattern_image
        view.favorite = data?.pattern?.favorite
        view.noteCount = data?.noteCount
        view.onCardClickedListener = { onCardClickedListener?.invoke(data) }
    }

}