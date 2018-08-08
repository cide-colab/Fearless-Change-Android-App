package de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
import android.view.WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.CardDetailActivity
import de.thkoeln.fherborn.fearlesschange.ui.activities.CardDetailActivity.Companion.CARD_ID_KEY
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import kotlinx.android.synthetic.main.layout_card_popup.*

class CardPopup(context: Context, val card: Card): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.layout_card_popup)
        setParams()
        popup_card.card = card
        setListeners()
    }

    private fun setListeners() {
        popup_details_btn.setOnClickListener {
            startCardDetailActivity()
        }
    }

    private fun startCardDetailActivity() {
        val intent = Intent(context, CardDetailActivity::class.java)
        intent.putExtra(CARD_ID_KEY, card.id)
        context.startActivity(intent)
    }

    private fun setParams() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        dismiss()
        return true
    }
}