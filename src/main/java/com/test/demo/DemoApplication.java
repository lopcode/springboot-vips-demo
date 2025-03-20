package com.test.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import app.photofox.vipsffm.Vips;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return _ -> {
			Vips.init();
		};
	}

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder()
				.codecs(configurer -> configurer
						.defaultCodecs()
						.maxInMemorySize(50 * 1024 * 1024));
	}
}
