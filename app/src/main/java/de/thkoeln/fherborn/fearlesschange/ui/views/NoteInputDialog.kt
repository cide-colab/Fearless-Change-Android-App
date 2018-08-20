package de.thkoeln.fherborn.fearlesschange.ui.views

import android.app.Dialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import de.thkoeln.fherborn.fearlesschange.R
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.databinding.LayoutEditNoteBinding


/**
 * A Dialog to get user input for a note title and description
 */
class NoteInputDialog(context: Context?, private val noteTitle: String = "", private val noteText: String = "") : Dialog(context) {

    lateinit var model: NoteInputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog()
        initViewModel()
        initDataBinding()
    }

    private fun initViewModel() {
        model = ViewModelProviders.of(ownerActivity as AppCompatActivity).get(NoteInputViewModel::class.java)
        model.setValues(noteTitle, noteText)
    }

    private fun initDataBinding() {
        val binding = DataBindingUtil.inflate<LayoutEditNoteBinding>(LayoutInflater.from(context), R.layout.layout_edit_note, null, false)
        setContentView(binding.root)
        binding.model = model
    }

    /**
     * Sets the LayoutParams and gravity and hides the background of the dialog
     */
    private fun initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    /**
     * Dismisses the Dialog if the User clicks beside the Views
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        dismiss()
        return true
    }
}

class NoteInputViewModel: ViewModel() {
    var title: String = ""
    var description: String = ""
    var onConfirmListener: ((title: String, description: String) -> Unit)? = null

    fun setValues(noteTitle: String, noteDescription: String) {
        title = noteTitle
        description = noteDescription
    }

    fun onConfirm() {
        onConfirmListener?.invoke(title, description)
    }
}