package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternRepository
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.Statistic
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticAction
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticRepository

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternViewModel(context: Application) : BasicViewModel(context) {

    private val patternRepository by lazy { PatternRepository(context) }
    private val statisticRepository by lazy { StatisticRepository(context) }

    private var cardCount = 50
    private val generateNewRandomPattern = Event<List<Int>>()

    val openPatternDetailDialogEvent = Event<Long>()

    fun getPattern(id: Long?) = patternRepository.get(forceGetNonNullId(id))
    fun getPatterns(): LiveData<List<PatternInfo>> = Transformations.map(patternRepository.getAllInfo()) { getAndSendMessageIfNullOrEmpty(it, R.string.message_no_pattern_found) }
    fun getFavorites(): LiveData<List<PatternInfo>> = Transformations.map(patternRepository.getFavoritesInfo()) { getAndSendMessageIfNullOrEmpty(it, R.string.message_no_favorites_found) }

    fun favoriteButtonClicked(cardId: Long?) {
        patternRepository.switchFavorite(forceGetNonNullId(cardId))
    }

    fun getPatternOfTheDay(): LiveData<PatternInfo> =
            Transformations.switchMap(patternRepository.getAllIds()) { ids ->
                calculatePatternOfTheDay(ids)?.let { id -> patternRepository.getInfo(id) }
            }

    fun getRandomPatterns(): LiveData<List<PatternInfo>> =
            Transformations.switchMap(generateNewRandomPattern) { randomInts ->
                Transformations.switchMap(patternRepository.getAllIds()) { ids ->
                    cardCount = ids.size
                    mapToIds(ids, randomInts)?.let {
                        patternRepository.getInfos(it)
                    }
                }
            }


    private fun mapToIds(ids: List<Long>?, randomInts: List<Int>): List<Long>? =
            ids?.let {
                randomInts.map { ids[it % ids.size] }
            }


    fun generateNewRandomPatterns() {
        generateNewRandomPattern.invoke((0..cardCount).shuffled().subList(0, 3))
    }

    fun getMostClickedPattern(): LiveData<PatternInfo> =
            Transformations.switchMap(statisticRepository.getMostCommonByAction(StatisticAction.CLICK)) {
                it?.patternId?.let { patternRepository.getInfo(it) }
            }

    fun cardPreviewClicked(patternInfo: PatternInfo?) {
        patternInfo?.pattern?.id?.let {
            statisticRepository.insert(Statistic(patternId = it, action = StatisticAction.CLICK))
            openPatternDetailDialogEvent.invoke(it)
        } ?: sendMessage(R.string.could_not_find_pattern)
    }

    private fun calculatePatternOfTheDay(cardIds: List<Long>): Long? {
        val currentDay = (System.currentTimeMillis() / 1000 / 60 / 60 / 24)
        return when (cardIds.size) {
            0 -> null
            else -> cardIds[(currentDay % cardIds.size).toInt()]
        }
    }

}

