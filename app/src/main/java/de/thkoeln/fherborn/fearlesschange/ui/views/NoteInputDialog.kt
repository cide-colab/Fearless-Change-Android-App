package de.thkoeln.fherborn.fearlesschange.ui.views

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.layout_edit_note.*

//TODO Logik für notes auslagern?
class NoteInputDialog(context: Context?, private val noteTitle: String = "", private val noteText: String = "") : Dialog(context) {

    var onConfirmListener: ((title: String, description: String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_edit_note)
        prepareDialog()
        setValues()
        setListeners()
        setFont()
    }

    private fun setFont() {
        val font = Typeface.createFromAsset(context.assets, "fonts/note_font.ttf")
        note_title.typeface = font
        note_description.typeface = font
    }

    private fun setValues() {
        add_note_title.text = context.getString(R.string.label_new_note)
        note_title.setText(noteTitle)
        note_description.setText(noteText)
    }

    private fun setListeners() {
        add_note_cancel.setOnClickListener { dismiss() }
        add_note_confirm.setOnClickListener {
            onConfirmListener?.invoke(
                    note_title.text.toString(),
                    note_description.text.toString()
            )
        }
    }

    private fun prepareDialog() {
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