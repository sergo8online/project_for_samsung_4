package com.scientiahub.utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OpenAIManager {
    private static OpenAIManager instance;
    private final OpenAIService service;
    private final String BASE_URL = "https://api.openai.com/v1/";
    private String apiKey;

    private OpenAIManager() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        service = retrofit.create(OpenAIService.class);
    }

    public static synchronized OpenAIManager getInstance() {
        if (instance == null) {
            instance = new OpenAIManager();
        }
        return instance;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void askQuestion(String question, AIResponseCallback callback) {
        if (apiKey == null) {
            callback.onError("API ключ не установлен");
            return;
        }

        ChatRequest request = new ChatRequest();
        request.addMessage("user", question);

        service.createChatCompletion("Bearer " + apiKey, request)
            .enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String answer = response.body().getFirstAnswer();
                        callback.onSuccess(answer);
                    } else {
                        callback.onError("Ошибка API: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    callback.onError("Ошибка сети: " + t.getMessage());
                }
            });
    }

    public interface AIResponseCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    private interface OpenAIService {
        @POST("chat/completions")
        Call<ChatResponse> createChatCompletion(
            @Header("Authorization") String authorization,
            @Body ChatRequest request
        );
    }

    private static class ChatRequest {
        @SerializedName("model")
        private String model = "gpt-3.5-turbo";

        @SerializedName("messages")
        private List<Message> messages;

        public void addMessage(String role, String content) {
            messages.add(new Message(role, content));
        }

        private static class Message {
            @SerializedName("role")
            private String role;

            @SerializedName("content")
            private String content;

            Message(String role, String content) {
                this.role = role;
                this.content = content;
            }
        }
    }

    private static class ChatResponse {
        @SerializedName("choices")
        private List<Choice> choices;

        public String getFirstAnswer() {
            if (choices != null && !choices.isEmpty()) {
                return choices.get(0).message.content;
            }
            return null;
        }

        private static class Choice {
            @SerializedName("message")
            private Message message;
        }

        private static class Message {
            @SerializedName("content")
            private String content;
        }
    }
} 
