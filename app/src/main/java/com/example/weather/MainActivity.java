package com.example.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.seminar9.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView tempertaturaCurenta, tempMin, tempMax;
    TextView tempCurentaTW,tempMinTW,tempMaxTW;
    Spinner localitate;
    ImageView Poza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localitate = findViewById(R.id.spinnerLocalitate);
        tempertaturaCurenta = findViewById(R.id.tvTemperaturaCurenta);
        tempMin = findViewById(R.id.tvtemperataturaMinima);
        tempMax = findViewById(R.id.tvTemperaturaMaxima);

        tempCurentaTW = findViewById(R.id.tvTempActualaDecor);
        tempMinTW= findViewById(R.id.tvTempMinimaDecor);
        tempMaxTW = findViewById(R.id.tvTempMaximaDecor);

        Poza = findViewById(R.id.ivPoza);

        // Populare Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.localitati_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localitate.setAdapter(adapter);

        // functia de schimbare a datelor prin selectarea item urilor din spinner
        localitate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLocalitate = localitate.getSelectedItem().toString();
                new Thread(() -> {
                    Prognoza prognoza = Serviciu.preiaPrognoza(selectedLocalitate);
                    runOnUiThread(() -> {
                        actualizati(prognoza);
                    });
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        //starea initiala a spinner ului
        new Thread(() -> {
            Prognoza prognoza = Serviciu.preiaPrognoza("Bucuresti");
            runOnUiThread(() -> {
                actualizati(prognoza);
            });
        }).start();
    }

    private void actualizati(Prognoza prognoza) {
        tempertaturaCurenta.setText(String.valueOf(prognoza.getTempCt()));
        //am adaugat o metoda in prognoza pt index
        int index = prognoza.gasesteIndexLocalitate(prognoza.getLocalitate());
        localitate.setSelection(index);
        tempMin.setText(String.valueOf(prognoza.getTempMin()));
        tempMax.setText(String.valueOf(prognoza.getTempMax()));
        if(prognoza.getTempCt()<10)
        {
            Poza.setImageResource(R.drawable.cloudy);
        }
        else {
            Poza.setImageResource(R.drawable.sunny);
        }
    }

}

