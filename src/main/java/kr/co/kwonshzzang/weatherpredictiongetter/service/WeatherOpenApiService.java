package kr.co.kwonshzzang.weatherpredictiongetter.service;

import kr.co.kwonshzzang.weatherpredictiongetter.config.WeatherOpenAPIProperties;
import kr.co.kwonshzzang.weatherpredictiongetter.dto.WeatherPrediction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WeatherOpenApiService {
    private final WebClient webClient;
    private final WeatherOpenAPIProperties properties;

    public WeatherPrediction doCallBack(String baseDate, String baseTime) throws UnsupportedEncodingException {
        return webClient.get()
                .uri(
                        UriComponentsBuilder.fromUriString(properties.baseUrl())
                                .queryParam("serviceKey", URLEncoder.encode(properties.serviceKey(), "UTF-8"))
                                .queryParam("numOfRows", properties.numOfRows())
                                .queryParam("pageNo", properties.pageNo())
                                .queryParam("dataType", properties.dataType())
                                .queryParam("base_date", baseDate)
                                .queryParam("base_time", baseTime)
                                .queryParam("nx", properties.nx())
                                .queryParam("ny", properties.ny())
                                .build(true)
                                .toUri()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherPrediction.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())  // GET 요청에 대해 3초의 타임아웃을 설정한다.
                // 폴백(fallback)으로 빈모노 객체를 반환한다.
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty()) //404 응답을 받으면 빈 객체를 반환한다.
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(100))  // 지수 백오프를 재시도 전략으로 사용한다.
//                        // 100밀리초의 초기 백오프로 총 3회까지 시도한다.
                )
                .onErrorResume(Exception.class, exception -> Mono.empty())
                // 3회의 재시도 동안 오류가 발생하면 예외를 포작하고 빈 객체를 반환한다.
                .block();
    }
}
