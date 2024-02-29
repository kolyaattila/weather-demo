package com.example.weatherdemo.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherForecastResultDto {
    private String name;
    private String temperature;
    private String wind;
}
