package de.thkoeln.colab.fearlesschange.view.search

import android.view.View
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.layout.LayoutViewHolder
import de.thkoeln.colab.fearlesschange.persistance.keyword.Keyword
import kotlinx.android.synthetic.main.filter_item.view.*


class KeywordViewHolder : LayoutViewHolder<Keyword>(R.layout.filter_item) {
    override fun bind(view: View, value: Keyword) {
        view.filter_item_text.text = value.keyword
    }
}
