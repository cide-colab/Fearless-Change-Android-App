package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.v2.ui.customs.card.PatternCardPreview

/**
 * Adapter to adapt a pattern to a recycler view
 *
 * @author Florian Herborn on 10.08.2018.
 * @since 0.0.1
 * @property patterns patterns to show and notesCount of each pattern
 * @see RecyclerView.Adapter
 * @see CardActionListener
 */
class PatternRecyclerGridAdapter(var patterns: List<PatternInfo> = listOf()) : RecyclerView.Adapter<PatternRecyclerGridAdapter.PatternViewHolder>() {

    var patternClickedListener: ((PatternInfo?) -> Unit)? = null

    fun updatePatterns(patterns: List<PatternInfo>) {
        this.patterns = patterns
        notifyDataSetChanged()
    }

    /**
     * Inflates ItemView and creates a ViewHolder with this view
     * @see RecyclerView.Adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PatternViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_card_grid_item, parent, false))

    /**
     * Binds a viewholder to
     * @see RecyclerView.Adapter
     */
    override fun onBindViewHolder(holder: PatternRecyclerGridAdapter.PatternViewHolder, position: Int) {
        holder.bindCard(patterns[position])
    }

    /**
     * returns the size of the cardList
     * @see RecyclerView.Adapter
     */
    override fun getItemCount(): Int {
        return patterns.size
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    inner class PatternViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var patternPreviewAdapter = PatternCardPreviewAdapter().apply {
            itemView.findViewById<PatternCardPreview>(R.id.pattern_preview_item).setAdapter(this)
        }

        fun bindCard(cardWithNoteCount: PatternInfo) {
            patternPreviewAdapter.change(cardWithNoteCount)
            patternPreviewAdapter.onCardClickedListener = patternClickedListener
        }
    }
}