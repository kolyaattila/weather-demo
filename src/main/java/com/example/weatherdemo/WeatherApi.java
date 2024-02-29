package com.example.weatherdemo;

import com.example.weatherdemo.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherApi {

    private final WeatherService weatherService;

    @GetMapping("/api/weather")
    public WeatherResponseDto getWeather(@RequestParam("city") List<String> cities) {
        return weatherService.getWeather(cities);
    }

}
