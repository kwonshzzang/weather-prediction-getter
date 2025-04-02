package kr.co.kwonshzzang.weatherpredictiongetter.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CurrentTimeAnalyzer {
    public String[] getCurrentDateTme() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
        return dateTimeFormatter.format(now).split(" ");
    }
}