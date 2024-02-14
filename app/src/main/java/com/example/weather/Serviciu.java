package com.example.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Serviciu {

    static String url_geo = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=en&format=json";
    static String url_prognoza = "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current=temperature_2m,is_day,weather_code&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=%d";

    // Cazuri particulare pentru București, Focșani și Bacău
    static String url_geo_buc = "https://geocoding-api.open-meteo.com/v1/search?name=Bucuresti&count=1&language=en&format=json";
    static String url_prognoza_buc = "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current=temperature_2m,is_day,weather_code&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=%d";

    static String url_geo_focsani = "https://geocoding-api.open-meteo.com/v1/search?name=Focsani&count=1&language=en&format=json";
    static String url_prognoza_focsani = "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current=temperature_2m,is_day,weather_code&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=%d";

    static String url_geo_bacau = "https://geocoding-api.open-meteo.com/v1/search?name=Bacau&count=1&language=en&format=json";
    static String url_prognoza_bacau = "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current=temperature_2m,is_day,weather_code&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=%d";

    public static Prognoza preiaPrognoza(String localitate) {
        Prognoza prognoza = new Prognoza();
        prognoza.setLocalitate(Prognoza.Localitate.valueOf(localitate));

        Coordonate coord;
        String urlGeo, urlPrognoza;

        // selectare URL in functie de loc
        switch (localitate) {
            case "Bucuresti":
                urlGeo = url_geo_buc;
                urlPrognoza = url_prognoza_buc;
                break;
            case "Focsani":
                urlGeo = url_geo_focsani;
                urlPrognoza = url_prognoza_focsani;
                break;
            case "Bacau":
                urlGeo = url_geo_bacau;
                urlPrognoza = url_prognoza_bacau;
                break;
            default:
                urlGeo = String.format(url_geo, localitate);
                urlPrognoza = String.format(url_prognoza, 0.0, 0.0, 1);
                break;
        }

        coord = obtineCoordonate(urlGeo);

        try {
            URL url = new URL(String.format(urlPrognoza, coord.getLatitudine(), coord.getLongitudine(), 1));
            //obține conținutul JSON
            String json = new Scanner(url.openStream()).useDelimiter("\\A").next();

            JSONObject jsonObject = new JSONObject(json);

            JSONObject current = jsonObject.getJSONObject("current");

            //validare vector (ca sa nu avem un vector fara elemente)
            prognoza.setTempCt(current.getDouble("temperature_2m"));

            JSONObject daily = jsonObject.getJSONObject("daily");

            prognoza.setTempMin(daily.getJSONArray("temperature_2m_min").getDouble(0));
            prognoza.setTempMax(daily.getJSONArray("temperature_2m_max").getDouble(0));
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        return prognoza;
    }

    private static Coordonate obtineCoordonate(String url) {
        Coordonate coord = new Coordonate();
        try {
            URL geoUrl = new URL(url);

            //obține conținutul JSON
            String json = new Scanner(geoUrl.openStream()).useDelimiter("\\A").next();

            JSONObject jsonObject = new JSONObject(json);

            JSONArray results = jsonObject.getJSONArray("results");

            coord.setLatitudine(results.getJSONObject(0).getDouble("latitude"));
            coord.setLongitudine(results.getJSONObject(0).getDouble("longitude"));
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return coord;
    }
}
