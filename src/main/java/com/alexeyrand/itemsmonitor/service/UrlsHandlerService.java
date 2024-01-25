package com.alexeyrand.itemsmonitor.service;

import com.alexeyrand.itemsmonitor.api.dto.UrlDto;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UrlsHandlerService {
    private static Map<String, String> urlsMap = new HashMap<>();


    public void setUrls(UrlDto urlDto) {
        urlsMap.put(urlDto.getName(), urlDto.getUrl());
        try {
            Files.write(Paths.get("new_urls.txt"),
                    urlsMap.entrySet().stream().map(k -> k.getKey() + " -> " + k.getValue()).collect(Collectors.toList()),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.lines(Paths.get("new_urls.txt"), StandardCharsets.UTF_8).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("fsfsf");
            throw new RuntimeException(e);
        }
    }

    public List<String> getUrls() {
        List<String> lines = new ArrayList<>();
        Reader r;
        try {
            r = new FileReader("new_urls.txt");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s = new Scanner(r);
        try {
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
        } finally {
            s.close();
        }

        return lines;
    }
}
