package de.thkoeln.fherborn.fearlesschange.adapters

import android.content.Context
import android.widget.ArrayAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword

class SearchAutoCompleteKeywordAdapter(context: Context, resource: Int, objects: List<Keyword>):
        ArrayAdapter<Keyword>(context, resource, objects) {
    override fun getAutofillOptions(): Array<CharSequence> {
        return n
    }
}