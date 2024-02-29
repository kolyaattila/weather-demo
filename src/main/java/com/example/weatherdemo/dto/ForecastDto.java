package com.example.weatherdemo.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastDto {
    private int day;
    private double temperature;
    private double wind;
}