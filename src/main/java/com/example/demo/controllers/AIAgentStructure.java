package com.example.demo.controllers;

import com.example.demo.output.Movie;
import com.example.demo.output.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AIAgentStructure {
    ChatClient chatClient;

    public AIAgentStructure(ChatClient.Builder build) {
        chatClient = build
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
    @GetMapping("/askAgent")
    public MovieList askLLM(String query) {

        String systemMessage = """
                        Vous etes un specialiste dans le domaine du cinema
                        Repond a la question de l'utilisateur a ce propos
                        """;

        return chatClient.prompt()
                .system(systemMessage)
                .user(query)
                .call()
                .entity(MovieList.class);

    }

}
