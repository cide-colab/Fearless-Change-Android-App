package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.Keyword
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.KeywordRepository
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternRepository
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event

class SearchViewModel(context: Application) : BasicViewModel(context) {
    private val keywordRepository by lazy { KeywordRepository(context) }
    private val patternRepository by lazy { PatternRepository(context) }
    private val searchClickedEvent = Event<List<Keyword>?>()
    val selectedKeywords = MutableLiveData<List<Keyword>>()
    val openCreateNoteDialogEvent = Event<Long>()

    init {
        selectedKeywords.value = emptyList()
    }

    fun getNotSelectedKeywords(): LiveData<List<Keyword>> = Transformations.switchMap(selectedKeywords) { selectedKeywords ->
        Transformations.map(keywordRepository.getAllKeywords()) {
            it.filterNot { keyword -> selectedKeywords.contains(keyword) }
        }
    }

    fun addKeywordClicked(keyword: Keyword) {
        selectedKeywords.value = selectedKeywords.value?.plus(keyword)
    }

    fun onSearchClicked() {
        searchClickedEvent.invoke(selectedKeywords.value)
    }

    fun getSearchResult() = Transformations.switchMap(searchClickedEvent) { keywords ->
        patternRepository.getByKeywordIds(keywords?.map { k -> k.id } ?: emptyList())
    }

    fun removeKeyword(keyword: Keyword) {
        selectedKeywords.value = selectedKeywords.value?.minus(keyword)
    }
//    fun addKeywordClicked(keywordString: String) = Transformations
//            .switchMap(keywordRepository.getKeywordByKeyword(keywordString)) {keyword ->
//                keyword?.let {
//                    val elementExists = selectedKeywords.value?.contains(keyword) ?: false
//                    if(!elementExists) {
//                        selectedKeywords.value?.add(keyword)
//                        // TODO View aktualiseren
//                    } else {
//                        // TODO Snackbar R.string.search_error_already_in_list
//                    }
//                } ?: run {
//                    // TODO Snackbar R.string.search_error_keyword_not_found
//                }
//            }
}