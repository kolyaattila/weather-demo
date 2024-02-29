package com.example.weatherdemo.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherResponseDto {
    private List<WeatherForecastResultDto> result;
}
