package de.thkoeln.colab.fearlesschange.ui.notes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.data.persistance.note.Note
import de.thkoeln.colab.fearlesschange.data.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.helper.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.ui.BasicPatternViewModel

class PatternNotesViewModel(application: Application, args: PatternNotesFragmentArgs) : BasicPatternViewModel(application) {
    val createNoteConfirmed: (title: String, description: String) -> Unit = { title, description ->
        noteRepository.insert(Note(title = title, text = description, patternId = args.patternId))
    }

    private val noteRepository = NoteRepository(application)

    val addNoteButtonClicked: () -> Unit = { createDialogEvent.invoke(args.patternId) }
    val onItemDeleteListener: (item: Note) -> Unit = { noteRepository.delete(it) }

    val createDialogEvent = SingleActionLiveData<Long>()
    val notes = noteRepository.getNotesForPattern(args.patternId)

}


@Suppress("UNCHECKED_CAST")
class PatternNotesViewModelFactory(private val application: Application, private val args: PatternNotesFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternNotesViewModel(application, args) as T
    }
}