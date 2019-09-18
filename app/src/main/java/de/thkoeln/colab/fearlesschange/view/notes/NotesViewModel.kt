package de.thkoeln.colab.fearlesschange.view.notes

import android.app.Application
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoinRepository
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import de.thkoeln.colab.fearlesschange.persistance.todos.TodoRepository
import kotlinx.coroutines.runBlocking


//data class NoteData(val noteData: Note, val labels: List<Badge>, val todos: List<Todo>)

class NotesViewModel(application: Application) : InteractiveViewModel(application) {

    private val patternRepository by lazy { PatternRepository(application) }
    private val noteRepo = NoteRepository(application)
    private val todoRepo = TodoRepository(application)
    private val noteLabelJoinRepo = NoteLabelJoinRepository(application)


    val updateTodo: (todo: Todo, newState: Boolean) -> Unit = { todo, state ->
        runBlocking {
            todoRepo.update(todo.copy(state = state))
        }
    }
    val patternClicked: (patternData: PatternPreviewData) -> Unit = {
        notifyPatternClicked(it.pattern)
        navigator {
            navigate(NotesFragmentDirections.actionNavNotesToPatternDetailSwipeFragment(longArrayOf(it.pattern.id), it.pattern.id))
        }

    }

    fun getNoteData(callback: (List<PatternNoteData>) -> Unit) = runBlocking {
        val notes = noteRepo.getAll()
        val result = notes.map {
            val patternNoteData = NoteData(it, noteLabelJoinRepo.getByNote(it.id), todoRepo.getByNote(it.id))
            val pattern = patternRepository.get(it.patternId)
            PatternNoteData(pattern, patternNoteData)
        }
        callback(result)
    }

    fun deleteNote(note: Note) {
        runBlocking {
            noteRepo.delete(note)
        }
    }


    fun addNote(note: Note) {
        runBlocking {
            noteRepo.insert(note)
        }
    }


}