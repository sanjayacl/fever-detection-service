package com.sanja.feverdetection.dto;

public class TemperatureResponse {

    private double celsius;
    private double fahrenheit;

    public double getCelsius() { return celsius; }
    public void setCelsius(double celsius) { this.celsius = celsius; }

    public double getFahrenheit() { return fahrenheit; }
    public void setFahrenheit(double fahrenheit) { this.fahrenheit = fahrenheit; }
}
