package kr.co.kwonshzzang.weatherpredictiongetter.service;

import kr.co.kwonshzzang.weatherpredictiongetter.config.WeatherOpenAPIProperties;
import kr.co.kwonshzzang.weatherpredictiongetter.dto.WeatherPrediction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherDataTransferService {
    private final WebClient webClient;
    private final WeatherOpenAPIProperties properties;

    public void  doService(WeatherPrediction weatherPrediction) {
        webClient.post()
                .uri(properties.dataTransferUrl())
                .bodyValue(weatherPrediction)
                .retrieve()
                .bodyToMono(WeatherPrediction.class)
                .block();
        log.info("Send Weather Observation - {}", weatherPrediction);
    }
}
