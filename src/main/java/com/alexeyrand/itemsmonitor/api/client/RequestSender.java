package com.alexeyrand.itemsmonitor.api.client;

import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;

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

    public void statusRequest(URI url, MessageDto messageDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessageDto = null;
        try {
            jsonMessageDto = mapper.writeValueAsString(messageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonMessageDto);
        System.out.println(url);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .timeout(Duration.ofMinutes(2))
                .POST(HttpRequest.BodyPublishers.ofString(jsonMessageDto))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//        try {
//            String response = responseFuture.get().body();
//            System.out.println(response);
//        } catch (ExecutionException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

}
