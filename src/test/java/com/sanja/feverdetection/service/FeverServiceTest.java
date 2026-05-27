package com.sanja.feverdetection.service;

import com.sanja.feverdetection.client.TemperatureConversionClient;
import com.sanja.feverdetection.dto.FeverResponse;
import com.sanja.feverdetection.dto.TemperatureResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeverServiceTest {

    @Mock
    private TemperatureConversionClient conversionClient;

    @InjectMocks
    private FeverService feverService;

    @Test
    void shouldDetectFeverWhenTemperatureAboveThreshold() {
        when(conversionClient.convertCelsiusToFahrenheit(39.0)).thenReturn(response(39.0, 102.2));

        FeverResponse result = feverService.checkFever(39.0);

        assertThat(result.isFever()).isTrue();
        assertThat(result.getCelsius()).isEqualTo(39.0);
        assertThat(result.getFahrenheit()).isEqualTo(102.2);
    }

    @Test
    void shouldNotDetectFeverWhenTemperatureBelowThreshold() {
        when(conversionClient.convertCelsiusToFahrenheit(36.0)).thenReturn(response(36.0, 96.8));

        FeverResponse result = feverService.checkFever(36.0);

        assertThat(result.isFever()).isFalse();
        assertThat(result.getCelsius()).isEqualTo(36.0);
        assertThat(result.getFahrenheit()).isEqualTo(96.8);
    }

    @Test
    void shouldDetectFeverWhenTemperatureAtExactThreshold() {
        // 38°C = 100.4°F — boundary must count as fever
        when(conversionClient.convertCelsiusToFahrenheit(38.0)).thenReturn(response(38.0, 100.4));

        FeverResponse result = feverService.checkFever(38.0);

        assertThat(result.isFever()).isTrue();
        assertThat(result.getFahrenheit()).isEqualTo(100.4);
    }

    @Test
    void shouldNotDetectFeverForNormalBodyTemperature() {
        when(conversionClient.convertCelsiusToFahrenheit(37.0)).thenReturn(response(37.0, 98.6));

        FeverResponse result = feverService.checkFever(37.0);

        assertThat(result.isFever()).isFalse();
    }

    private TemperatureResponse response(double celsius, double fahrenheit) {
        TemperatureResponse r = new TemperatureResponse();
        r.setCelsius(celsius);
        r.setFahrenheit(fahrenheit);
        return r;
    }
}
