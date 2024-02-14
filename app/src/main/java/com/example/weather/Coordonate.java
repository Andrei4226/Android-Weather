package com.example.weather;

public class Coordonate {
    private double latitudine,longitudine;
    public Coordonate()
    {}

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public Coordonate(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "Coordonate{" +
                "latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                '}';
    }
}
