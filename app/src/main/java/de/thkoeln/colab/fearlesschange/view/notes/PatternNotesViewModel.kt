package de.thkoeln.colab.fearlesschange.view.notes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.core.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository

class PatternNotesViewModel(application: Application, args: PatternNotesFragmentArgs) : BasicPatternViewModel(application) {

    private val id = args.patternId

    private val noteRepository = NoteRepository(application)

    val onItemDeleteListener: (item: Note) -> Unit = { noteRepository.delete(it) }

    val notes = noteRepository.getNotesForPattern(id)

    fun createNoteButtonClicked() {
        notifyAction(PatternNotesFragmentDirections.actionPatternNotesFragmentToCreateNoteFragment(id))
    }


}


@Suppress("UNCHECKED_CAST")
class PatternNotesViewModelFactory(private val application: Application, private val args: PatternNotesFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternNotesViewModel(application, args) as T
    }
}