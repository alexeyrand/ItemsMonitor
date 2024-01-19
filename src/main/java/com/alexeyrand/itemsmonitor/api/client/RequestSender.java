package com.alexeyrand.itemsmonitor.api.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RequestSender {

    public void postRequest(URI url, String body) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .timeout(Duration.ofMinutes(2))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            String response = responseFuture.get().toString();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public void statusRequest(URI url, String chatId, String messageId) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .timeout(Duration.ofMinutes(2))
                .POST(HttpRequest.BodyPublishers.ofString(chatId + " " + messageId))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

}
