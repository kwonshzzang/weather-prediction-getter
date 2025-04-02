package kr.co.kwonshzzang.weatherpredictiongetter.app;

import kr.co.kwonshzzang.weatherpredictiongetter.service.WeatherDataTransferService;
import kr.co.kwonshzzang.weatherpredictiongetter.service.WeatherOpenApiService;
import kr.co.kwonshzzang.weatherpredictiongetter.util.CurrentTimeAnalyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class WeatherPredictionGetAndTransfer {
    private final WeatherOpenApiService weatherOpenApiService;
    private final WeatherDataTransferService weatherDataTransferService;
    private final CurrentTimeAnalyzer currentTimeAnalyzer;

    @Async
    @Scheduled(fixedRate = 1000 * 60 * 60) //1시간마다
    public void doService() throws UnsupportedEncodingException {
        var currentDateTime = currentTimeAnalyzer.getCurrentDateTme();
        var callbackResult = weatherOpenApiService.doCallBack(currentDateTime[0], currentDateTime[1]);

        if(callbackResult == null || !callbackResult.getResponse().getHeader().getResultCode().equals("00"))
            return;

        weatherDataTransferService.doService(callbackResult);
    }

}
