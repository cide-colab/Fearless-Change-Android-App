package de.thkoeln.fherborn.fearlesschange.v2.ui.carddetail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.create_note_dialog.*

class CreateNoteDialog(context: Context?) : Dialog(context) {
    var onConfirmListener: ((String, String) -> Unit)? = null
    var dismissOnConfirm = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.create_note_dialog)
        create_note_confirm.setOnClickListener {
            if (dismissOnConfirm) dismiss()
            onConfirmListener?.invoke(
                    create_note_note_title.text.toString(),
                    create_note_note_text.text.toString()
            )
        }
    }
}