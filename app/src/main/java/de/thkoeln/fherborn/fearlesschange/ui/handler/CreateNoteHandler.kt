package de.thkoeln.fherborn.fearlesschange.ui.handler

import android.app.AlertDialog
import de.thkoeln.fherborn.fearlesschange.ui.viewmodels.CardNoteViewModel
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note

/**
 * Created by Florian on 21.08.2018.
 */
class CreateNoteHandler(private val viewModel: CardNoteViewModel, private val dialog: AlertDialog) {
    fun onConfirm(note: Note) {
        viewModel.createNote(note)
        dialog.dismiss()
    }
}