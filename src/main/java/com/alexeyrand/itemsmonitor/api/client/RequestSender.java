package com.alexeyrand.itemsmonitor.api.client;

import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public void statusRequest(URI url, String messageDto) {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        MessageDto test = new MessageDto();
        test.setChatId("2332");
        test.setMessageId(12341);
        final ObjectMapper mapper = new ObjectMapper();
        String jsonMessageDto;
        try {
            jsonMessageDto = mapper.writeValueAsString(messageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header()
                .timeout(Duration.ofMinutes(2))
                .POST(HttpRequest.BodyPublishers.ofString(jsonMessageDto))
                .build();
        System.out.println(request.toString());
        System.out.println(messageDto);
        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            String response = responseFuture.get().toString();
            System.out.println(response);
            System.out.println(messageDto);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
