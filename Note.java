package com.scientiahub.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    @NonNull
    private String noteId;
    private String userId;
    private String title;
    private String content;
    private long timestamp;
    private boolean isSynced;

    public Note(@NonNull String noteId, String userId, String title, String content) {
        this.noteId = noteId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
        this.isSynced = false;
    }

    // Getters and Setters
    @NonNull
    public String getNoteId() { return noteId; }
    public void setNoteId(@NonNull String noteId) { this.noteId = noteId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }
} 
