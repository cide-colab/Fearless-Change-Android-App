package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewBehavior

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardNotesBehavior(context: Context?) : CardViewBehavior {
    override fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction) {}
}