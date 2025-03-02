package com.scientiahub.database.dao;

import androidx.room.*;
import com.scientiahub.models.Note;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes WHERE userId = :userId ORDER BY timestamp DESC")
    List<Note> getAllNotes(String userId);

    @Query("SELECT * FROM notes WHERE noteId = :noteId LIMIT 1")
    Note getNoteById(String noteId);

    @Query("SELECT * FROM notes WHERE isSynced = 0")
    List<Note> getUnsyncedNotes();

    @Query("DELETE FROM notes WHERE userId = :userId")
    void deleteAllNotes(String userId);
} 
