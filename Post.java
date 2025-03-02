package com.scientiahub.models;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String postId;
    private String authorId;
    private String authorName;
    private String content;
    private String imageUrl;
    private long timestamp;
    private Map<String, Boolean> likes;
    private Map<String, Comment> comments;

    public Post() {
        // Required empty constructor for Firebase
    }

    public Post(String postId, String authorId, String authorName, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
        this.likes = new HashMap<>();
        this.comments = new HashMap<>();
    }

    // Getters and Setters
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public Map<String, Boolean> getLikes() { return likes; }
    public void setLikes(Map<String, Boolean> likes) { this.likes = likes; }

    public Map<String, Comment> getComments() { return comments; }
    public void setComments(Map<String, Comment> comments) { this.comments = comments; }
} 
