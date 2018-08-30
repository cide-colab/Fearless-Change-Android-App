package de.thkoeln.fherborn.fearlesschange.data.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.os.Bundle
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternRepository


class PatternDetailViewModel(context: Application) : BasicViewModel(context) {

    private val patternRepository by lazy { PatternRepository(context) }

    var selectedPatternId: MutableLiveData<Long> = MutableLiveData()
    var patternIdList: MutableLiveData<LongArray> = MutableLiveData()
    var selectedPattern: LiveData<PatternInfo> = Transformations.switchMap(selectedPatternId) {
        patternRepository.getInfo(it)
    }
    fun setSelectedPatternIdFromBundle(arguments: Bundle?, selectedPatternIdKey: String) {
        selectedPatternId.postValue(forceGetNonNullId(arguments?.getLong(selectedPatternIdKey)))
    }

    fun setPatternIdListFromBundle(arguments: Bundle?, patternIdListKey: String) {
        patternIdList.postValue(arguments?.getLongArray(patternIdListKey)?: LongArray(0))
    }

    fun setSelectedPatternId(patternId: Long) {
        selectedPatternId.postValue(patternId)
    }

}

