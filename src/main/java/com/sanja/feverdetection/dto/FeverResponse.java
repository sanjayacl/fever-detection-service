package com.sanja.feverdetection.dto;

public class FeverResponse {

    private double celsius;
    private double fahrenheit;
    private boolean fever;

    public FeverResponse() {}

    public FeverResponse(double celsius, double fahrenheit, boolean fever) {
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.fever = fever;
    }

    public double getCelsius() { return celsius; }
    public void setCelsius(double celsius) { this.celsius = celsius; }

    public double getFahrenheit() { return fahrenheit; }
    public void setFahrenheit(double fahrenheit) { this.fahrenheit = fahrenheit; }

    public boolean isFever() { return fever; }
    public void setFever(boolean fever) { this.fever = fever; }
}
