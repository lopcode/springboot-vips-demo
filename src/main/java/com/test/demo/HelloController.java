package com.test.demo;

import java.lang.foreign.Arena;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import app.photofox.vipsffm.VImage;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/")
    public String index() {
        WebClient client = webClientBuilder.build();

        Mono<byte[]> mono = client.get()
                .uri("http://www.lenna.org/full/l_hires.jpg")
                .retrieve()
                .bodyToMono(byte[].class);
        byte[] bytes = mono.block();

        try (Arena arena = Arena.ofConfined()) {
            VImage sourceImage = VImage.newFromBytes(arena, bytes);

            sourceImage.writeToFile(
                    Paths.get("")
                            .resolve("saved.png")
                            .toAbsolutePath()
                            .toString());
        }

        return "Finished";
    }

}
