package com.example.mustofa.shipinspector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class BeforeMainActivity extends AppCompatActivity {

    private String[] namaKapal = new String[]{"Kapal 1","Kapal 2","Kapal 3","Kapal 4","Kapal 5"};
    Spinner kapal;
    Button bShip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_main);
        kapal = (Spinner) findViewById(R.id.pilih_kapal);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,namaKapal);
        kapal.setAdapter(arrayAdapter);
        /*kapal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BeforeMainActivity.this,MainActivity.class);
                intent.putExtra("nama_kapal",kapal.getSelectedItem().toString());
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        bShip = (Button) findViewById(R.id.button_ship);
        bShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeforeMainActivity.this,MainActivity.class));
            }
        });
    }
}
