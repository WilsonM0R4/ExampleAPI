package com.example.wilson.exampleapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView name, alpha2Code, alpha3Code, capital,
            region, subregion, population, area, nativeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initFields();
        fillData(getIntent());
    }

    private void initFields() {
        name = findViewById(R.id.name);
        alpha2Code = findViewById(R.id.alpha2code);
        alpha3Code = findViewById(R.id.alpha3code);
        capital = findViewById(R.id.capital);
        region = findViewById(R.id.region);
        subregion = findViewById(R.id.subregion);
        population = findViewById(R.id.population);
        area = findViewById(R.id.area);
        nativeName = findViewById(R.id.native_name);
    }

    private void fillData(Intent args) {

        name.setText("Name: "+args.getStringExtra("name"));
        alpha2Code.setText("Alpha2code: "+args.getStringExtra("alpha2code"));
        alpha3Code.setText("Alpha3code: "+args.getStringExtra("alpha3code"));
        capital.setText("capital: "+args.getStringExtra("capital"));
        region.setText("Region: "+args.getStringExtra("region"));
        subregion.setText("Subregion: "+args.getStringExtra("subregion"));
        population.setText("Population: "+args.getIntExtra("population", 0));
        area.setText("Area: "+args.getDoubleExtra("area", 0));
        nativeName.setText("Native name: "+args.getStringExtra("nativeName"));

    }
}
