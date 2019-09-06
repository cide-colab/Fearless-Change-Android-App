package de.thkoeln.colab.fearlesschange.view.notes

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.label.LabelRepository
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoinRepository
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import de.thkoeln.colab.fearlesschange.persistance.todos.TodoRepository
import kotlinx.coroutines.runBlocking


data class PatternNoteData(val note: Note, val labels: List<Label>, val todos: List<Todo>)

class PatternNotesViewModel(application: Application, args: PatternNotesFragmentArgs) : BasicPatternViewModel(application) {

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

    fun loadNotes(callback: (List<PatternNoteData>) -> Unit) {
        runBlocking {
            val notes = noteRepository.getNotesForPattern(id)
            val result = notes.map {
                PatternNoteData(
                        it,
                        noteLabelJoinRepository.getByNote(it.id),
                        todoRepository.getByNote(it.id)
                )
            }
            callback(result)
        }
    }

    //TODO Get todos for note
    //TODO Get label for note
    //TODO Update TODO

    fun createNoteButtonClicked() {
        notifyAction(PatternNotesFragmentDirections.actionPatternNotesFragmentToCreateNoteFragment(id))
    }

//    fun getNoteLabels(note: Note) = noteLabelJoinRepository.getByNote(note.id)

//    fun getNoteTodos(note: Note) = todoRepository.getByNote(note.id)

    fun updateTodo(todo: Todo, state: Boolean) = runBlocking {
        Log.d("Update TODO", "try ${todo.id} state $state")
        val updated = todoRepository.update(todo.copy(state = state))
        val dbTodo = todoRepository.get(todo.id)
        Log.d("DB TODO", "db  $dbTodo")
        Log.d("Update TODO", "updated  $updated rows")
        Log.d("Update TODO", "done ${todo.id} state $state")

    }


}


@Suppress("UNCHECKED_CAST")
class PatternNotesViewModelFactory(private val application: Application, private val args: PatternNotesFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternNotesViewModel(application, args) as T
    }
}