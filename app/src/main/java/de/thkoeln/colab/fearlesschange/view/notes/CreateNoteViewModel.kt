package de.thkoeln.colab.fearlesschange.view.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository

class CreateNoteViewModel(application: Application, private val args: CreateNoteFragmentArgs) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application)

    fun onCreateNoteClicked(title: String, note: String) {
        noteRepository.insert(Note(title = title, text = note, patternId = args.patternId))
    }

}



@Suppress("UNCHECKED_CAST")
class CreateNoteViewModelFactory(private val application: Application, private val args: CreateNoteFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNoteViewModel(application, args) as T
    }
}