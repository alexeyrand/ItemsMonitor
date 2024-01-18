package com.alexeyrand.itemsmonitor.api.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.statusCode());
                //.thenApply(HttpResponse::body)
                //.thenAccept(System.out::println);


//        final RestTemplate restTemplate = new RestTemplate();
//        final String stringPosts = restTemplate.getForObject(url, String.class);
//        System.out.println(stringPosts);
    }

}
