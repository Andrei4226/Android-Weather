package com.example.weather;

public class Prognoza {
    private Localitate localitate;
    private double tempCt,tempMin,tempMax;
    public enum Localitate{
        Bucuresti,
        Focsani,
        Bacau
    }

    public Prognoza() {
    }

    public Prognoza(Localitate localitate, double tempCt, double tempMin, double tempMax) {
        this.localitate = localitate;
        this.tempCt = tempCt;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    public Localitate getLocalitate() {
        return localitate;
    }

    public void setLocalitate(Localitate localitate) {
        this.localitate = localitate;
    }

    public double getTempCt() {
        return tempCt;
    }

    public void setTempCt(double tempCt) {
        this.tempCt = tempCt;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }
    public int gasesteIndexLocalitate(Prognoza.Localitate localitateCautata) {
        Prognoza.Localitate[] localitati = Prognoza.Localitate.values();
        for (int i = 0; i < localitati.length; i++) {
            if (localitateCautata == localitati[i]) {
                return i;
            }
        }
        return 0;
    }

}
