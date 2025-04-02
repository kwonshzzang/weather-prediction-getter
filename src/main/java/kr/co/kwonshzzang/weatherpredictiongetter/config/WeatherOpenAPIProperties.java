package kr.co.kwonshzzang.weatherpredictiongetter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather-open-api")
public record WeatherOpenAPIProperties(
        String baseUrl,
        String serviceKey,
        String dataTransferUrl,
        Integer nx,
        Integer ny,
        Integer numOfRows,
        Integer pageNo,
        String dataType
) {
}
