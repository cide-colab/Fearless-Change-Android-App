package de.thkoeln.colab.fearlesschange.data.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.Statistic
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.StatisticRepository
import de.thkoeln.colab.fearlesschange.helper.events.ActionLiveData

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternViewModel(context: Application) : BasicViewModel(context) {

    private val patternRepository by lazy { PatternRepository(context) }
    private val statisticRepository by lazy { StatisticRepository(context) }


    class RandomCardMediator(private val source: LiveData<List<PatternInfo>>) : MediatorLiveData<Pair<List<PatternInfo>, Boolean>>() {

        private var cardCount = 50
        private var count = 3
        private val generateNewRandomPattern = ActionLiveData<List<Int>>()
        private var newGenerated = false

        init {
            addSource(source) { value ->
                value?.let {
                    cardCount = it.size
                    setNewValue(pattern = it)
                }
            }
            addSource(generateNewRandomPattern) { value -> setNewValue(indices = value) }
            generateNewRandomPatterns()
        }

        private fun setNewValue(indices: List<Int>? = generateNewRandomPattern.value, pattern: List<PatternInfo>? = source.value) {
            if (pattern == null) return
            value = indices?.map { pattern[it] }?.let { Pair(it, newGenerated) }
            newGenerated = false
        }

        fun generateNewRandomPatterns() {
            newGenerated = true
            generateNewRandomPattern.invoke((0 until cardCount).shuffled().subList(0, count))
        }
    }


    val randomPattern = RandomCardMediator(patternRepository.getAllInfo())
    val openPatternDetailDialogEvent = ActionLiveData<Pair<LongArray, Long>>()

    fun getPattern(id: Long?) = patternRepository.getInfo(forceGetNonNullId(id))
    fun getPatterns(): LiveData<List<PatternInfo>> = Transformations.map(patternRepository.getAllInfo()) { getAndSendMessageIfNullOrEmpty(it, R.string.message_no_pattern_found) }
    fun getFavorites(): LiveData<List<PatternInfo>> = Transformations.map(patternRepository.getFavoritesInfo()) { getAndSendMessageIfNullOrEmpty(it, R.string.message_no_favorites_found) }

    fun favoriteButtonClicked(cardId: Long?) {
        patternRepository.switchFavorite(forceGetNonNullId(cardId))
    }

    val patternOfTheDay: LiveData<PatternInfo> =
            Transformations.switchMap(patternRepository.getAllIds()) { ids ->
                calculatePatternOfTheDay(ids)?.let { id -> patternRepository.getInfo(id) }
            }

    fun generateNewRandomPatterns() {
        randomPattern.generateNewRandomPatterns()
    }


    val mostClickedPattern: LiveData<PatternInfo> by lazy {
        statisticRepository.getMostCommonByAction(StatisticAction.CLICK)
    }

    fun cardPreviewClicked(patternIds: LongArray, selectedPatternId: Long?) {
        selectedPatternId?.let {
            statisticRepository.insert(Statistic(patternId = it, action = StatisticAction.CLICK))
            openPatternDetailDialogEvent.invoke(Pair(patternIds, it))
        } ?: sendMessage(R.string.message_could_not_find_pattern)
    }

    private fun calculatePatternOfTheDay(cardIds: List<Long>): Long? {
        val currentDay = (System.currentTimeMillis() / 1000 / 60 / 60 / 24)
        return when (cardIds.size) {
            0 -> null
            else -> cardIds[(currentDay % cardIds.size).toInt()]
        }
    }

}

