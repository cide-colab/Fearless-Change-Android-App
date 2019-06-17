package de.thkoeln.colab.fearlesschange.ui.search

import android.app.Application
import android.util.Log
import de.thkoeln.colab.fearlesschange.asLiveData
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.Keyword
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.KeywordRepository
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.helper.events.DynamicLiveData
import de.thkoeln.colab.fearlesschange.helper.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.map
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternViewModel

class SearchViewModel(application: Application) : BasicPatternViewModel(application) {

    private val keywordRepository = KeywordRepository(application)

    private val patternDynamic = DynamicLiveData<List<PatternInfo>>()
    val pattern = patternDynamic.asLiveData()

    private val unselectedKeywordsDynamic = DynamicLiveData<List<Keyword>>()
    val unselectedKeywords = unselectedKeywordsDynamic.asLiveData()

    init {
        refreshKeywords()
    }

    private val selectedKeywordsDynamic = SingleActionLiveData<List<Keyword>>()
    val selectedKeywords = selectedKeywordsDynamic.asLiveData()

    private val keywords = mutableListOf<Keyword>()

    val keywordAddedListener: (Keyword) -> Unit = { keyword ->
        keywords.add(keyword)
        selectedKeywordsDynamic.invoke(keywords)
        patternDynamic.newSource(patternRepository.getByKeywordIds(keywords.map { it.id }))
    }

    val onKeywordDeleted: (keyword: Keyword) -> Unit = { keyword ->
        keywords.remove(keyword)
        selectedKeywordsDynamic.invoke(keywords)
        patternDynamic.newSource(patternRepository.getByKeywordIds(keywords.map { it.id }))
    }

    private fun refreshKeywords() {
        unselectedKeywordsDynamic.newSource(keywordRepository.getAllKeywords().map { keywords -> keywords.filter { !this.keywords.contains(it) }.also { Log.e("TEST", it.toString()) } })
    }

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo)
            notifyAction(SearchFragmentDirections.actionNavSearchToPatternDetailSwipeFragment(longArrayOf(it.pattern.id), it.pattern.id))
        }
    }

}
