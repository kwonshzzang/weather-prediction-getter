package kr.co.kwonshzzang.weatherpredictiongetter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class WeatherPredictionGetterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherPredictionGetterApplication.class, args);
    }

}
