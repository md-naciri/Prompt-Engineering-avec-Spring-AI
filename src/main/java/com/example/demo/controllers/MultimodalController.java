package com.example.demo.controllers;

import com.example.demo.output.CINModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultimodalController {
    private ChatClient chatClient;

    @Value("classpath:/images/img1.jpg")
    private Resource image;

    @Value("classpath:/images/img2.jpg")
    private Resource image2;

    public MultimodalController(ChatClient.Builder build) {
        this.chatClient = build.build();
    }

    @GetMapping("/describe")
    public String describeImagetxt(){
        return chatClient.prompt()
                .system("Donne moi une description de l'image")
                .user(u ->
                        u.text("Decrit cett image")
                                .media(MediaType.IMAGE_JPEG, image)
                )
                .call()
                .content();

    }


    @GetMapping("/describe")
    public CINModel describeImage(){
        return chatClient.prompt()
                .system("Donne moi une description de l'image")
                .user(u ->
                            u.text("Decrit cett image")
                                    .media(MediaType.IMAGE_JPEG, image)
                )
                .call()
                .entity(CINModel.class);

    }

    @PostMapping(value = "/askImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String askImage(String query,@RequestParam(name = "file") MultipartFile file )throws IOException {
        byte[] bytes = file.getBytes();
        return chatClient.prompt()
                .system("repond a la question de l'utilisateur a propos de l'image manuscrite fournit")
                .user(u ->
                        u.text(query)
                                .media(MediaType.IMAGE_JPEG, new ByteArrayResource(bytes))
                )
                .call()
                .content();

    }
}
