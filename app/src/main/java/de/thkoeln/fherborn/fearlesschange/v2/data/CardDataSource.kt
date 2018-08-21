package de.thkoeln.fherborn.fearlesschange.v2.data

import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

interface CardDataSource {

    interface GetCardCallback {
        fun onCardLoaded(card: Card)
        fun onDataNotAvailable()
    }

    fun getCard(cardId: Long, callback: GetCardCallback)

}