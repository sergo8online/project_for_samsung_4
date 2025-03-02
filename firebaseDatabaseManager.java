package com.scientiahub.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.scientiahub.models.Post;
import com.scientiahub.models.Comment;
import com.scientiahub.models.User;

public class FirebaseDatabaseManager {
    private static FirebaseDatabaseManager instance;
    private final DatabaseReference database;
    private final String USERS_REF = "users";
    private final String POSTS_REF = "posts";
    private final String COMMENTS_REF = "comments";
    private final String CHATS_REF = "chats";

    private FirebaseDatabaseManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static synchronized FirebaseDatabaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseManager();
        }
        return instance;
    }

    // User operations
    public Task<Void> createUser(User user) {
        return database.child(USERS_REF).child(user.getUid()).setValue(user);
    }

    public DatabaseReference getUserReference(String userId) {
        return database.child(USERS_REF).child(userId);
    }

    // Post operations
    public Task<Void> createPost(Post post) {
        String postId = database.child(POSTS_REF).push().getKey();
        post.setPostId(postId);
        return database.child(POSTS_REF).child(postId).setValue(post);
    }

    public Task<Void> updatePost(Post post) {
        return database.child(POSTS_REF).child(post.getPostId()).setValue(post);
    }

    public Task<Void> deletePost(String postId) {
        return database.child(POSTS_REF).child(postId).removeValue();
    }

    public Query getPostsQuery() {
        return database.child(POSTS_REF).orderByChild("timestamp");
    }

    // Comment operations
    public Task<Void> addComment(String postId, Comment comment) {
        String commentId = database.child(POSTS_REF).child(postId)
            .child(COMMENTS_REF).push().getKey();
        comment.setCommentId(commentId);
        return database.child(POSTS_REF).child(postId)
            .child(COMMENTS_REF).child(commentId).setValue(comment);
    }

    // Chat operations
    public DatabaseReference getChatReference(String chatId) {
        return database.child(CHATS_REF).child(chatId);
    }

    public Task<Void> sendMessage(String chatId, String message, String senderId) {
        String messageId = database.child(CHATS_REF).child(chatId).push().getKey();
        return database.child(CHATS_REF).child(chatId).child(messageId)
            .setValue(new ChatMessage(senderId, message));
    }

    // Inner class for chat messages
    private static class ChatMessage {
        public String senderId;
        public String message;
        public long timestamp;

        public ChatMessage(String senderId, String message) {
            this.senderId = senderId;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
    }
} 
