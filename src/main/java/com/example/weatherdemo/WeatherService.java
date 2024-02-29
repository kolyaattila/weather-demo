package com.example.weatherdemo;

import com.example.weatherdemo.dto.ForecastDto;
import com.example.weatherdemo.dto.WeatherDto;
import com.example.weatherdemo.dto.WeatherForecastResultDto;
import com.example.weatherdemo.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherWebClient weatherWebClient;
    private final WeatherCsvService weatherCsvService;

    public WeatherResponseDto getWeather(List<String> cities) {
        var resultDtos = Flux.fromIterable(cities)
                .flatMap(this::getCityWeather)
                .sort(Comparator.comparing(WeatherForecastResultDto::getName))
                .collectList()
                .block();

        weatherCsvService.writeToCsv(resultDtos);

        return WeatherResponseDto.builder()
                .result(resultDtos)
                .build();
    }

    private Flux<WeatherForecastResultDto> getCityWeather(String city) {
        var weather = weatherWebClient.getWeather(city);

        return weather.map(weatherDto -> mapWeather(weatherDto, city));
    }

    private WeatherForecastResultDto mapWeather(WeatherDto weatherDto, String city) {
        return Optional.ofNullable(weatherDto)
                .map(w -> buildResult(weatherDto, city))
                .orElse(emptyResult(city));
    }

    private static WeatherForecastResultDto buildResult(WeatherDto weatherDto, String city) {
        var averageTemperature = weatherDto.getForecast().stream()
                .mapToDouble(ForecastDto::getTemperature)
                .average();
        var averageWind = weatherDto.getForecast().stream()
                .mapToDouble(ForecastDto::getWind)
                .average();

        WeatherForecastResultDto resultDto = WeatherForecastResultDto.builder().name(city).build();

        if (averageTemperature.isPresent()) {
            resultDto.setTemperature(String.valueOf(averageTemperature.getAsDouble()));
        } else {
            resultDto.setTemperature("");
        }

        if (averageWind.isPresent()) {
            resultDto.setWind(String.valueOf(averageWind.getAsDouble()));
        } else {
            resultDto.setWind("");
        }


        return resultDto;
    }

    private WeatherForecastResultDto emptyResult(String city) {
        return WeatherForecastResultDto.builder()
                .name(city)
                .temperature("")
                .wind("")
                .build();
    }


}
