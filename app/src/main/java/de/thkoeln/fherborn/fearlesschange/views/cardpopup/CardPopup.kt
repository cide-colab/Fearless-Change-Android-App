package de.thkoeln.fherborn.fearlesschange.views.cardpopup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import android.view.Window.FEATURE_NO_TITLE
import de.thkoeln.fherborn.fearlesschange.db.Card
import kotlinx.android.synthetic.main.layout_card_popup.*

class CardPopup(context: Context, val card: Card): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.layout_card_popup)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        popup_card.card = card
    }
}