package de.thkoeln.colab.fearlesschange.data.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.os.Bundle
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.helper.events.Event


class PatternDetailViewModel(context: Application) : BasicViewModel(context) {

    private val patternRepository by lazy { PatternRepository(context) }

    var selectedPatternId: MutableLiveData<Long> = MutableLiveData()

    val setViewPagerPositionEvent: Event<Int> = Event()
    val sharePatternEvent: Event<Pattern> = Event()
    val setupPagingAdapterEvent: Event<LongArray> = Event()

    val selectedPatternInfo: LiveData<PatternInfo> = Transformations.switchMap(selectedPatternId) {
        it?.let { id -> patternRepository.getInfo(id) }
    }

    fun extractSelectedPattern(arguments: Bundle?, selectedPatternIdKey: String): Long {
        val id = forceGetNonNullId(arguments?.getLong(selectedPatternIdKey))
        selectedPatternId.value = id
        return id
    }

    fun extractPatternIdList(arguments: Bundle?, patternIdListKey: String) {
        val list = arguments?.getLongArray(patternIdListKey) ?: LongArray(0)
        setupPagingAdapterEvent.invoke(list)
        selectedPatternId.value?.let { setViewPagerPositionEvent.invoke(list.indexOfFirst { id -> id == it }) }
    }

    fun onSwipePager(patternId: Long) {
        selectedPatternId.postValue(patternId)
    }

    fun onSharePressed() {
        selectedPatternInfo.value?.pattern?.let {
            sharePatternEvent.invoke(it)
        }?:sendMessage(R.string.massage_no_pattern_to_share)
    }
}

