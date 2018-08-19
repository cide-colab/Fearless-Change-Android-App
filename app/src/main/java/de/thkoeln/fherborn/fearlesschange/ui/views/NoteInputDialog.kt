package de.thkoeln.fherborn.fearlesschange.ui.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.layout_edit_note.*

/**
 * A Dialog to get user input for a note title and description
 */
class NoteInputDialog(context: Context?, private val noteTitle: String = "", private val noteText: String = "") : Dialog(context) {

    var onConfirmListener: ((title: String, description: String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_edit_note)
        prepareDialog()
        setValues(noteTitle, noteText)
        setListeners()
    }

    /**
     * Presets the Title and Description input fields
     * @param noteTitle Title of the Note
     * @param noteDescription Description of the Note
     */
    private fun setValues(noteTitle: String, noteDescription: String) {
        note_title.setText(noteTitle)
        note_description.setText(noteDescription)
    }

    /**
     * Adds a onClickListener that invoke the given onConfirmListener with the inserted Values
     */
    private fun setListeners() {
        add_note_confirm.setOnClickListener {
            onConfirmListener?.invoke(
                    note_title.text.toString(),
                    note_description.text.toString()
            )
        }
    }

    /**
     * Sets the LayoutParams and gravity and hides the background of the dialog
     */
    private fun prepareDialog() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    /**
     * Dismisses the Dialog if the User clicks beside the Views
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        dismiss()
        return true
    }
}