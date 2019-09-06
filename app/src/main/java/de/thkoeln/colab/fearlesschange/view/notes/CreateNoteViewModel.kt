package de.thkoeln.colab.fearlesschange.view.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.label.LabelRepository
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoinRepository
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import de.thkoeln.colab.fearlesschange.persistance.todos.TodoRepository
import kotlinx.coroutines.runBlocking

class CreateNoteViewModel(application: Application, private val args: CreateNoteFragmentArgs) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application)
    private val labelRepository = LabelRepository(application)
    private val todoRepository = TodoRepository(application)
    private val patternLabelTodoRepository = NoteLabelJoinRepository(application)

    fun onSaveClicked(labels: List<Label>, note: String, todosRaw: List<Todo>) {
        runBlocking {
            val noteId = noteRepository.insert(Note(patternId = args.patternId, text = note))
            val todos = todosRaw.map { it.copy(noteId = noteId) }
            todoRepository.insert(todos)
            val labelIds = labelRepository.createOrUpdate(labels)
            patternLabelTodoRepository.join(noteId, labelIds)
        }
    }

    fun getLabels(callback: (List<Label>) -> Unit) {
        runBlocking {
            callback(labelRepository.getAll())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CreateNoteViewModelFactory(private val application: Application, private val args: CreateNoteFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNoteViewModel(application, args) as T
    }
}