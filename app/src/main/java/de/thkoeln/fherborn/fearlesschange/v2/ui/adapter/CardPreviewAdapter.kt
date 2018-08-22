package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.R.id.*
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardInfo
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedImage

/**
 * Created by florianherborn on 22.08.18.
 */
class CardPreviewAdapter(container: View): SingleViewAdapter<CardInfo>(container) {
    
    var onCardClickedListener: ((CardInfo?) -> Unit)? = null
    
    override fun setData(data: CardInfo?) {
        container.findViewById<TextView>(card_preview_card_title).text = data?.card?.title
        container.findViewById<TextView>(card_preview_card_summary).text = data?.card?.summary
        container.findViewById<ImageView>(card_preview_card_image)
                .setOptimizedImage(data?.card?.pictureName, R.drawable.default_pattern_image)
        container.findViewById<ImageView>(card_preview_favorite_icon).setImageResource(when (data?.card?.favorite) {
            true -> R.drawable.ic_favorite_full_white
            else -> R.drawable.ic_favorite_white
        })

        when (data?.noteCount) {
            null, 0 -> {
                container.findViewById<TextView>(card_preview_notes_count).visibility = View.INVISIBLE
                container.findViewById<ImageView>(card_preview_notes_icon).visibility = View.INVISIBLE
            }
            else -> {
                container.findViewById<TextView>(card_preview_notes_count).text = data.noteCount.toString()
                container.findViewById<TextView>(card_preview_notes_count).visibility = View.VISIBLE
                container.findViewById<ImageView>(card_preview_notes_icon).visibility = View.VISIBLE
            }
        }
        container.findViewById<View>(card_preview_card).setOnClickListener { onCardClickedListener?.invoke(data) }
    }

}