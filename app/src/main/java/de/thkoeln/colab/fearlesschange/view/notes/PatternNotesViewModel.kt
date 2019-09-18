package de.thkoeln.colab.fearlesschange.view.notes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.label.LabelRepository
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoinRepository
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import de.thkoeln.colab.fearlesschange.persistance.todos.TodoRepository
import kotlinx.coroutines.runBlocking


data class NoteData(val note: Note, val labels: List<Label>, val todos: List<Todo>)

class PatternNotesViewModel(application: Application, args: PatternNotesFragmentArgs) : InteractiveViewModel(application) {

    private val id = args.patternId

    private val noteRepository = NoteRepository(application)
    private val todoRepository = TodoRepository(application)
    private val labelRepository = LabelRepository(application)
    private val noteLabelJoinRepository = NoteLabelJoinRepository(application)

    fun deleteNote(note: Note) {
        runBlocking {
            noteRepository.delete(note)
        }
    }


    fun addNote(note: Note) {
        runBlocking {
            noteRepository.insert(note)
        }
    }


//    val notes = noteRepository.getNotesForPattern(id)

    fun loadNotes(callback: (List<NoteData>) -> Unit) {
        runBlocking {
            val notes = noteRepository.getNotesForPattern(id)
            val result = notes.map {
                NoteData(
                        it,
                        noteLabelJoinRepository.getByNote(it.id),
                        todoRepository.getByNote(it.id)
                )
            }
            callback(result)
        }
    }

    //TODO Get todos for noteData
    //TODO Get badge for noteData
    //TODO Update TODO

    fun createNoteButtonClicked() {
        navigator {
            navigate(PatternNotesFragmentDirections.actionPatternNotesFragmentToCreateNoteFragment(id))
        }
    }

//    fun getNoteLabels(noteData: Note) = noteLabelJoinRepository.getByNote(noteData.id)

//    fun getNoteTodos(noteData: Note) = todoRepository.getByNote(noteData.id)

    fun updateTodo(todo: Todo, state: Boolean) = runBlocking {
        todoRepository.update(todo.copy(state = state))
        todoRepository.get(todo.id)
    }


}


@Suppress("UNCHECKED_CAST")
class PatternNotesViewModelFactory(private val application: Application, private val args: PatternNotesFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternNotesViewModel(application, args) as T
    }
}