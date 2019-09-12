package de.thkoeln.colab.fearlesschange.persistance.label

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

class LabelRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).labelDao()

    suspend fun insert(label: Label) = dao.insert(label)

    suspend fun update(label: Label) = dao.update(label)

    suspend fun createOrUpdate(labels: List<Label>): List<Long> {
        return labels.map {
            val existing = getByName(it.name)
            if (existing == null) {
                insert(it)
            } else {
                update(it.copy(id = existing.id))
                existing.id
            }
        }
    }

    suspend fun insert(labels: List<Label>) = dao.insert(labels)

    fun get(id: Long) = dao.get(id)

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun getLike(name: String) = dao.getLike(name)

    suspend fun getAll() = dao.getAll()

    suspend fun deleteAll() = dao.deleteAll()

}