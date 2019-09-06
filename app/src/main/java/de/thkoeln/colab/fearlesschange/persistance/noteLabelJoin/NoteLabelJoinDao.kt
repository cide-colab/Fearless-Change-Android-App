package de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.thkoeln.colab.fearlesschange.persistance.label.Label

@Dao
interface NoteLabelJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteLabelJoin: List<NoteLabelJoin>)

    @Query("SELECT * FROM labels INNER JOIN note_label_join ON labels.id=note_label_join.labelId WHERE note_label_join.noteId=:noteId")
    suspend fun getByNoteId(noteId: Long): List<Label>
}