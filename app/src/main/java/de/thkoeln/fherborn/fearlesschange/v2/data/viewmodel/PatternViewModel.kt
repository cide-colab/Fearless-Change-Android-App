package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternRepository
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.Statistic
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticAction
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticRepository

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternViewModel(context: Application) : AndroidViewModel(context) {

    private val patternRepository by lazy { PatternRepository(context) }
    private val statisticRepository by lazy { StatisticRepository(context) }

    val patternPreviewClickedEvent = Event<Long>()
    private var randomIds: List<Long>? = null
    private var generated = false

    fun getPattern(id: Long?) = patternRepository.get(getCheckedPatternId(id))
    fun getPatterns() = patternRepository.getAll()
    fun getFavorites() = patternRepository.getFavorites()

    fun switchFavorite(cardId: Long?) {
        patternRepository.switchFavorite(getCheckedPatternId(cardId))
    }

    fun getPatternOfTheDay(): LiveData<PatternInfo> =
            Transformations.switchMap(patternRepository.getAllIds()) { ids ->
                calculatePatternOfTheDay(ids)?.let { id -> patternRepository.getInfo(id) }
            }

    fun getRandomPatterns(): LiveData<List<PatternInfo>> =
            Transformations.switchMap(patternRepository.getAllIds()) { ids ->
                getRandomIds(ids)?.let {
                    patternRepository.getInfos(it)
                }
            }

    private fun getRandomIds(ids: List<Long>?): List<Long>? =
            ids?.let {
                when {
                    randomIds == null || !generated -> {
                        generated = true
                        randomIds = calculateRandomPatterns(ids, 3)
                        randomIds
                    }
                    else -> randomIds

                }
            }


    fun calculateNewRandomPatterns() {
        generated = false
    }

    fun getMostClickedPattern(): LiveData<PatternInfo> =
            Transformations.switchMap(statisticRepository.getMostCommonByAction(StatisticAction.CLICK)) {
                it?.patternId?.let { patternRepository.getInfo(it) }
            }

    fun cardPreviewClicked(patternInfo: PatternInfo?) {
        getCheckedPatternId(patternInfo?.pattern?.id).let {
            statisticRepository.insert(Statistic(patternId = it, action = StatisticAction.CLICK))
            patternPreviewClickedEvent.invoke(it)
        }
    }

    private fun getCheckedPatternId(id: Long?) = id
            ?: throw IllegalArgumentException("Missing pattern id")

    private fun calculateRandomPatterns(ids: List<Long>, count: Int) =
            ids.shuffled().subList(0, Math.min(count, ids.size))

    private fun calculatePatternOfTheDay(cardIds: List<Long>): Long? {
        val currentDay = (System.currentTimeMillis() / 1000 / 60 / 60 / 24)
        return when (cardIds.size) {
            0 -> null
            else -> cardIds[(currentDay % cardIds.size).toInt()]
        }
    }

}