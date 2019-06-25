package de.thkoeln.colab.fearlesschange.persistance.keyword

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

/**
 * Created by florianherborn on 06.08.18.
 */
class KeywordRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).keywordDao()

    fun getAllKeywords() = dao.getKeywords()

    fun getKeywordByKeyword(keyword: String) = dao.getKeywordByKeyword(keyword)
}