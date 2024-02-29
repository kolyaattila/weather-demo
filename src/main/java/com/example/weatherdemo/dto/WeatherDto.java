package com.example.weatherdemo.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDto {
    private double temperature;
    private double wind;
    private String description;
    private List<ForecastDto> forecast;

}
