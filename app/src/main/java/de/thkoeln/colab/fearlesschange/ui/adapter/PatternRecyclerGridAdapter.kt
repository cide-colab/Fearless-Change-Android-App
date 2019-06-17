package de.thkoeln.colab.fearlesschange.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.ui.customs.card.PatternCardPreview

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

    var patternClickedListener: (PatternInfo?) -> Unit = {}

    fun updatePatterns(patterns: List<PatternInfo>) {
        this.patterns = patterns
        notifyDataSetChanged()
    }

    /**
     * Inflates ItemView and creates a ViewHolder with this view
     * @see RecyclerView.Adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PatternViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pattern_card_grid_item, parent, false))

    /**
     * Binds a viewholder to
     * @see RecyclerView.Adapter
     */
    override fun onBindViewHolder(holder: PatternViewHolder, position: Int) {
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
    class PatternViewHolder(itemView: View, private val patternClickedListener: (PatternInfo?) -> Unit = {}) : RecyclerView.ViewHolder(itemView) {

        private var patternPreviewAdapter = PatternCardPreviewAdapter().apply {
            itemView.findViewById<PatternCardPreview>(R.id.pattern_preview_item).setAdapter(this)
        }

        fun bindCard(cardWithNoteCount: PatternInfo) {
            patternPreviewAdapter.change(cardWithNoteCount)
            patternPreviewAdapter.onCardClickedListener = patternClickedListener
        }
    }
}