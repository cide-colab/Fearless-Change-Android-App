package de.thkoeln.colab.fearlesschange.data.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.helper.events.SingleActionLiveData


class PatternDetailViewModel(context: Application) : BasicViewModel(context) {

    private val patternRepository by lazy { PatternRepository(context) }

    var selectedPatternId: MutableLiveData<Long> = MutableLiveData()

    val setViewPagerPositionEvent: SingleActionLiveData<Int> = SingleActionLiveData()
    val sharePatternEvent: SingleActionLiveData<Pattern> = SingleActionLiveData()
    val setupPagingAdapterEvent: SingleActionLiveData<LongArray> = SingleActionLiveData()

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

