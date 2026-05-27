package com.sanja.feverdetection.service;

import com.sanja.feverdetection.client.TemperatureConversionClient;
import com.sanja.feverdetection.dto.FeverResponse;
import com.sanja.feverdetection.dto.TemperatureResponse;
import org.springframework.stereotype.Service;

@Service
public class FeverService {

    private static final double FEVER_THRESHOLD_FAHRENHEIT = 100.4;

    private final TemperatureConversionClient conversionClient;

    public FeverService(TemperatureConversionClient conversionClient) {
        this.conversionClient = conversionClient;
    }

    /**
     * Checks whether a patient has a fever based on their body temperature in Celsius.
     * <p>
     * Delegates the unit conversion to the temperature-conversion-service, then compares
     * the resulting Fahrenheit value against the clinical fever threshold of {@code 100.4°F}.
     *
     * @param celsius the patient's body temperature in degrees Celsius
     * @return a {@link FeverResponse} containing the original Celsius value, the converted
     *         Fahrenheit value, and a {@code fever} flag that is {@code true} if the
     *         temperature is at or above {@code 100.4°F}
     */
    public FeverResponse checkFever(double celsius) {
        TemperatureResponse conversion = conversionClient.convertCelsiusToFahrenheit(celsius);
        boolean hasFever = conversion.getFahrenheit() >= FEVER_THRESHOLD_FAHRENHEIT;
        return new FeverResponse(celsius, conversion.getFahrenheit(), hasFever);
    }
}
