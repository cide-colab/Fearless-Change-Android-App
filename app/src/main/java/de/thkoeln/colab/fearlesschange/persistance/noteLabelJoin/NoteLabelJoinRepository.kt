package de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

class NoteLabelJoinRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).noteLabelJoinDao()

    suspend fun join(noteId: Long, labelIds: List<Long>) = dao.insert(labelIds.map { NoteLabelJoin(noteId, it) })

    suspend fun getByNote(id: Long) = dao.getByNoteId(id)
    suspend fun getByLabelLike(query: String) = dao.getByLabel(query)
    suspend fun deleteAll() = dao.deleteAll()

}