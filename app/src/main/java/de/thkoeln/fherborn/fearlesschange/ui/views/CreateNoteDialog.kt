package de.thkoeln.fherborn.fearlesschange.ui.views

import android.app.Application
import android.app.Dialog
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import de.thkoeln.fherborn.fearlesschange.R
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.databinding.LayoutEditNoteBinding
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository


/**
 * A Dialog to get user input for a note title and description
 */
class CreateNoteDialog(private val activity: FragmentActivity, private val cardId: Long?) : Dialog(activity) {

    private lateinit var model: CreateNoteDialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        initViewModel()
        initDataBinding()
        initDialog()
    }

    private fun initViewModel() {
        model = ViewModelProviders.of(activity).get(CreateNoteDialogViewModel::class.java)
        model.cardId = cardId
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

class CreateNoteDialogViewModel(application: Application): AndroidViewModel(application) {

    private var noteRepository = NoteRepository(application)
    var cardId: Long? = null
    var title: String = "ascascasd"
    var description: String = ""



    fun createNote() {
        cardId?.let {
            noteRepository.insert(Note(title = title, description = description, cardId = it))
        }
    }

    val onTitleChanged = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { title = p0.toString() }
    }

    val onDescriptionChanged = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { description = p0.toString() }
    }

    fun onConfirm() {
        createNote()
    }
}