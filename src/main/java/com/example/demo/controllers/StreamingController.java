package com.example.demo.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamingController {

    private ChatClient chatClient;

    public StreamingController(ChatClient.Builder build) {
        chatClient = build.build();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE )
    public Flux<String> stream(String query) {
        return chatClient.prompt()
                .user(query)
                .stream()
                .content();
    }

    @GetMapping("/nostream")
    public String nostream(String query) {
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }
}
