package com.example.weatherdemo;

import com.example.weatherdemo.dto.WeatherDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class WeatherWebClient {
    WebClient client = WebClient.create("https://998d8129-2264-4a98-a92e-ba8bde4a4d1c.mock.pstmn.io");


    public Flux<WeatherDto> getWeather(String city) {
        return
                client.get()
                        .uri("/{city}", city)
                        .retrieve()
                        .bodyToFlux(WeatherDto.class)
                        .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.just(emptyResult()))
                        .log();
    }

    private WeatherDto emptyResult() {
        return WeatherDto.builder()
                .forecast(new ArrayList<>()).build();
    }


}
