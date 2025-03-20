package com.test.demo;

import java.lang.foreign.Arena;
import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import app.photofox.vipsffm.VImage;
import app.photofox.vipsffm.VipsOption;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public ResponseEntity<byte[]> index() {
        WebClient client = webClientBuilder.build();

        Mono<byte[]> mono = client.get()
                .uri("https://upload.wikimedia.org/wikipedia/commons/2/28/JPG_Test.jpg")
                .retrieve()
                .bodyToMono(byte[].class);
        byte[] bytes = mono.block();

        try (Arena arena = Arena.ofConfined()) {
            VImage sourceImage = VImage.newFromBytes(arena, bytes);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // https://www.libvips.org/API/current/libvips-resample.html#vips-thumbnail
            VImage thumbnail = sourceImage.thumbnailImage(
                800,
                VipsOption.Int("height", 32767)
            );
            logger.info("Resized width: " + thumbnail.getWidth() + ", height: " + thumbnail.getHeight());

            thumbnail.writeToStream(outputStream, ".png");
            byte[] imageBytes = outputStream.toByteArray();

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            // Return the image as response
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }
    }

}
