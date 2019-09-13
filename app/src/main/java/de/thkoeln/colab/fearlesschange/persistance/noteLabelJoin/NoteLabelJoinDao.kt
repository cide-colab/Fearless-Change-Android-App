package de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.note.Note

@Dao
interface NoteLabelJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteLabelJoin: List<NoteLabelJoin>)

    @Query("SELECT * FROM labels INNER JOIN note_label_join ON labels.id=note_label_join.labelId WHERE note_label_join.noteId=:noteId")
    suspend fun getByNoteId(noteId: Long): List<Label>

    @Query("SELECT n.id, n.patternId, n.text FROM noteData n INNER JOIN note_label_join j ON n.id=j.noteId INNER JOIN labels l ON l.id=j.labelId WHERE l.name LIKE '%' || :query || '%'")
    suspend fun getByLabel(query: String): List<Note>

    @Query("DELETE FROM note_label_join")
    suspend fun deleteAll()

}