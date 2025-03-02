package com.scientiahub.models;

public class Comment {
    private String commentId;
    private String authorId;
    private String authorName;
    private String content;
    private long timestamp;

    public Comment() {
        // Required empty constructor for Firebase
    }

    public Comment(String commentId, String authorId, String authorName, String content) {
        this.commentId = commentId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) { this.commentId = commentId; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
} 
