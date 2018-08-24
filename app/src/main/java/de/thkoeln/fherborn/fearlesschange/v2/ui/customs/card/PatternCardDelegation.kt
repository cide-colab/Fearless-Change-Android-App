package de.thkoeln.fherborn.fearlesschange.v2.ui.customs.card

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedBackground
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedImage
import de.thkoeln.fherborn.fearlesschange.v2.ui.customs.ViewDelegation

class PatternCardDelegation(
        private val view: View,
        val imageDefault: Int = R.drawable.default_pattern_image,
        val titleDefault: String = "",
        val summaryDefault: String = "",
        val problemDefault: String = "",
        val solutionDefault: String = "",
        val favoriteDefault: Boolean = false,
        val noteCountDefault: Int = 0,
        val backgroundDefault: Int = R.drawable.card_bg,
        val contentBackgroundDefault: Int = R.drawable.card_content_bg,
        val defaultClickListener: () -> Unit = {}
) {

    fun updateTitle(vararg titleViewIds: Int) =
            ViewDelegation(titleDefault) { setText(titleViewIds, it) }

    fun updateSummary(vararg summaryViewIds: Int) =
            ViewDelegation(summaryDefault) { setText(summaryViewIds, it) }

    fun updateProblem(vararg problemViewIds: Int) =
            ViewDelegation(problemDefault) { setText(problemViewIds, it) }

    fun updateSolution(vararg solutionViewIds: Int) =
            ViewDelegation(solutionDefault) { setText(solutionViewIds, it) }

    fun updateCardImage(vararg cardImageViewIds: Int) =
            ViewDelegation(imageDefault) { setImage(cardImageViewIds, it, imageDefault) }

    fun updateCardBackground(vararg cardImageViewIds: Int) =
            ViewDelegation(backgroundDefault) { setBackground(cardImageViewIds, it) }

    fun updateCardContentBackground(vararg cardImageViewIds: Int) =
            ViewDelegation(contentBackgroundDefault) { setBackground(cardImageViewIds, it) }

    fun updateFavoriteIcon(vararg favoriteIconViewIds: Int) =
            ViewDelegation(favoriteDefault) {
                setVisibility(favoriteIconViewIds, it)
            }

    fun updateFavoriteFab(vararg favoriteFabIds: Int) =
            ViewDelegation(favoriteDefault) {
                setImage(favoriteFabIds, when {
                    it -> R.drawable.ic_favorite_full_white
                    else -> R.drawable.ic_favorite_white
                })
            }

    fun updateNotesCount(noteCountViewId: Int, noteIconViewId: Int) =
            ViewDelegation(noteCountDefault) {
                setVisibility(intArrayOf(noteCountViewId, noteIconViewId), it > 0)
                setText(intArrayOf(noteCountViewId), it.toString())
            }


    fun updateClickListener(vararg cardViewIds: Int) =
            ViewDelegation(defaultClickListener) {
                setListeners(cardViewIds, it)
            }

    private fun setListeners(viewIds: IntArray, listener: () -> Unit) {
        viewIds.forEach { id ->
            view.findViewById<View>(id).setOnClickListener { listener.invoke() }
        }
    }

    private fun setText(viewIds: IntArray, text: String) {
        viewIds.forEach { id ->
            view.findViewById<TextView>(id).text = text
        }

    }

    private fun setImage(viewIds: IntArray, imageId: Int, defaultImageId: Int? = null) {
        viewIds.forEach { id ->
            view.findViewById<ImageView>(id).setOptimizedImage(imageId, defaultImageId)
        }
    }

    private fun setBackground(viewIds: IntArray, imageId: Int) {
        viewIds.forEach { id ->
            view.findViewById<View>(id).setOptimizedBackground(imageId)
        }
    }

    private fun setVisibility(viewIds: IntArray, flag: Boolean) {
        viewIds.forEach { id ->
            view.findViewById<View>(id).visibility = when {
                flag -> VISIBLE
                else -> INVISIBLE
            }
        }
    }
}
