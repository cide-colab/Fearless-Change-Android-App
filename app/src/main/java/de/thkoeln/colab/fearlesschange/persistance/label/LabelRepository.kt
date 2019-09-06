package de.thkoeln.colab.fearlesschange.persistance.label

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

class LabelRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).labelDao()

    suspend fun insert(label: Label) = dao.insert(label)

    suspend fun insert(labels: List<Label>) = dao.insert(labels)

    fun get(id: Long) = dao.get(id)

    fun getAll() = dao.getAll()

}