package de.thkoeln.colab.fearlesschange.view.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoinRepository
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import de.thkoeln.colab.fearlesschange.persistance.todos.TodoRepository
import de.thkoeln.colab.fearlesschange.view.notes.NoteData
import de.thkoeln.colab.fearlesschange.view.notes.PatternNoteData
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchViewModel(application: Application) : InteractiveViewModel(application) {


    private val patternRepository by lazy { PatternRepository(application) }
    private val notesRepo = NoteRepository(application)
    private val todoRepo = TodoRepository(application)
    private val noteLabelJoinRepo = NoteLabelJoinRepository(application)

    val notes: LiveData<List<PatternNoteData>> = MutableLiveData<List<PatternNoteData>>()
    val patternData: LiveData<List<PatternPreviewData>> = MutableLiveData<List<PatternPreviewData>>()

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo.pattern)
            navigator {
                navigate(SearchFragmentDirections.actionNavSearchToPatternDetailSwipeFragment(longArrayOf(it.pattern.id), it.pattern.id))
            }
        }
    }


    fun onQueryTextChange(text: String, searchInLabels: Boolean, searchInPattern: Boolean, searchInNotes: Boolean) {
        viewModelScope.launch {

            val pattern = if (searchInPattern) patternRepository.getLike(text) else listOf()
            val labelNotes = if (searchInLabels) noteLabelJoinRepo.getByLabelLike(text) else listOf()
            val notes = if (searchInNotes) notesRepo.getLike(text) else listOf()

            val mergedNotes = (labelNotes + notes).distinct().map {
                PatternNoteData(
                        patternRepository.get(it.patternId),
                        NoteData(it, noteLabelJoinRepo.getByNote(it.id), todoRepo.getByNote(it.id))
                )
            }

            (this@SearchViewModel.notes as MutableLiveData<List<PatternNoteData>>).postValue(mergedNotes)
            (this@SearchViewModel.patternData as MutableLiveData<List<PatternPreviewData>>).postValue(pattern)
        }
    }

    fun resetSearch() {
        (this@SearchViewModel.notes as MutableLiveData<List<PatternNoteData>>).postValue(listOf())
        (this@SearchViewModel.patternData as MutableLiveData<List<PatternPreviewData>>).postValue(listOf())
    }

    val updateTodo: (todo: Todo, newState: Boolean) -> Unit = { todo, state ->
        runBlocking {
            todoRepo.update(todo.copy(state = state))
        }
    }

}
