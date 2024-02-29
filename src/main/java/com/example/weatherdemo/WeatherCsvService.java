package com.example.weatherdemo;

import com.example.weatherdemo.dto.WeatherForecastResultDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherCsvService {

    private static final String CSV_HEADER = "Name, temperature, wind\n";

    public void writeToCsv(List<WeatherForecastResultDto> weatherStats) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (WeatherForecastResultDto weather : weatherStats) {
            csvContent.append(weather.getName()).append(",")
                    .append(weather.getTemperature()).append(",")
                    .append(weather.getWind())
                    .append("\n");
        }

        File file = new File(Objects.requireNonNull(getClass().getResource("/weather.csv")).getFile());
        try (
                FileWriter outputfile = new FileWriter(file, false);
        ) {
            outputfile.write(csvContent.toString());

            outputfile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
