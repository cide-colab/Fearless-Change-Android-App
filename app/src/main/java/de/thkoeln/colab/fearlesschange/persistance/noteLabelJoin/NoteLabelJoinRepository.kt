package de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin

import android.content.Context
import de.thkoeln.colab.fearlesschange.core.doAsync
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase
import de.thkoeln.colab.fearlesschange.persistance.label.Label

class NoteLabelJoinRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).noteLabelJoinDao()

    fun join(noteId: Long, labels: List<Label>) = doAsync { dao.insert(labels.map { NoteLabelJoin(noteId, it.id) }) }

    fun getByNote(id: Long) = dao.getByNoteId(id)

}