package com.sanja.feverdetection.client;

import com.sanja.feverdetection.dto.TemperatureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TemperatureConversionClient {

    private final RestTemplate restTemplate;
    private final String conversionServiceUrl;

    public TemperatureConversionClient(RestTemplate restTemplate,
                                       @Value("${temperature.conversion.service.url}") String conversionServiceUrl) {
        this.restTemplate = restTemplate;
        this.conversionServiceUrl = conversionServiceUrl;
    }

    public TemperatureResponse convertCelsiusToFahrenheit(double celsius) {
        String url = conversionServiceUrl + "/api/temperature/convert?celsius=" + celsius;
        return restTemplate.getForObject(url, TemperatureResponse.class);
    }
}
