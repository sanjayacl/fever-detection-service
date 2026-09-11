package com.sanja.feverdetection.client;

import com.sanja.feverdetection.dto.TemperatureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TemperatureConversionClient {

    private static final Logger log = LoggerFactory.getLogger(TemperatureConversionClient.class);

    private final RestTemplate restTemplate;
    private final String conversionServiceUrl;

    public TemperatureConversionClient(RestTemplate restTemplate,
                                       @Value("${temperature.conversion.service.url}") String conversionServiceUrl) {
        this.restTemplate = restTemplate;
        this.conversionServiceUrl = conversionServiceUrl;
    }

    public TemperatureResponse convertCelsiusToFahrenheit(double celsius) {
        String url = conversionServiceUrl + "/api/temperature/convert?celsius=" + celsius;
        log.debug("Calling temperature conversion service: {}", url);
        try {
            TemperatureResponse response = restTemplate.getForObject(url, TemperatureResponse.class);
            log.debug("Temperature conversion service responded: fahrenheit={}",
                    response != null ? response.getFahrenheit() : null);
            return response;
        } catch (RestClientException e) {
            log.error("Temperature conversion service call failed for url={}", url, e);
            throw e;
        }
    }
}
