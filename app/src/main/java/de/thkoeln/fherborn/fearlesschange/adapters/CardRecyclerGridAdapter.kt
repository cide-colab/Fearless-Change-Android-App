package de.thkoeln.fherborn.fearlesschange.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardWithNoteCount
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.*

/**
 * Adapter to adapt a card to a recycler view
 *
 * @author Florian Herborn on 10.08.2018.
 * @since 0.0.1
 * @property cards cards to show and notesCount of each card
 * @see RecyclerView.Adapter
 * @see CardActionListener
 */
class CardRecyclerGridAdapter(var cards: List<CardWithNoteCount> = listOf()) : RecyclerView.Adapter<CardRecyclerGridAdapter.CardViewHolder>(), CardViewBehaviorProcessor {

    override val cardBehaviors = mutableListOf<CardActionListener>()

    /**
     * Inflates ItemView and creates a ViewHolder with this view
     * @see RecyclerView.Adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_card_grid_item, parent, false))

    /**
     * Binds a viewholder to
     * @see RecyclerView.Adapter
     */
    override fun onBindViewHolder(holder: CardRecyclerGridAdapter.CardViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }

    /**
     * returns the size of the cardList
     * @see RecyclerView.Adapter
     */
    override fun getItemCount(): Int {
        return cards.size
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var cardView = itemView.findViewById<CardViewPreview>(R.id.overview_grid_card)

        fun bindCard(cardWithNoteCount: CardWithNoteCount) {
            cardView.card = cardWithNoteCount.card
            cardView.notesCount = cardWithNoteCount.noteCount
            cardView.addDistinctCardActionListener(cardBehaviors)
        }
    }
}