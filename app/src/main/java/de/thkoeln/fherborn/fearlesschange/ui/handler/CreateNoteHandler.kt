package de.thkoeln.fherborn.fearlesschange.ui.handler

import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.ui.viewmodels.CardNoteViewModel

/**
 * Created by Florian on 21.08.2018.
 */
class CreateNoteHandler(private val viewModel: CardNoteViewModel) {
    fun onConfirm(note: Note) {
        viewModel.createNote(note)
    }
}