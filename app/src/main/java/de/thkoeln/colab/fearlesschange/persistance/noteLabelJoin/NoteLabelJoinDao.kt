package de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteLabelJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(patternLabelTodo: List<NoteLabelJoin>)

    @Query("SELECT * FROM note_label_join WHERE noteId =:noteId")
    fun getByNoteId(noteId: Long): LiveData<List<NoteLabelJoin>>
}