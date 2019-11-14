package com.example.wilson.exampleapi.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wilson.exampleapi.DetailActivity;
import com.example.wilson.exampleapi.adapter.CountriesAdapter;
import com.example.wilson.exampleapi.controller.MainController;
import com.example.wilson.exampleapi.interfaces.UIListener;
import com.example.wilson.exampleapi.model.CountryModel;

import java.util.ArrayList;

public class MainViewModel implements UIListener {

    private MainController mController;
    private RecyclerView recyclerView;
    private TextView message;
    private Context context;

    public MainViewModel (Context context) {
        mController = new MainController(context);
        mController.setViewModel(this);
        this.context = context;
    }

    public void setRecyclerView(RecyclerView rv) {
        recyclerView = rv;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    @Override
    public void onUIUpdate() {
        message.setText("Loading data...");
        mController.requestCountryList();
    }

    @Override
    public void onDataUpdate(Object o) {

        final ArrayList<CountryModel> countries = (ArrayList<CountryModel>) o;
        CountriesAdapter adapter = new CountriesAdapter(countries, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                int position = recyclerView.getChildAdapterPosition(v);
                CountryModel model = countries.get(position);

                intent.putExtra("name", model.getName());
                intent.putExtra("alpha2code", model.getAlpha2Code());
                intent.putExtra("alpha3code", model.getAlpha3Code());
                intent.putExtra("capital", model.getCapital());
                intent.putExtra("region", model.getRegion());
                intent.putExtra("subregion", model.getSubregion());
                intent.putExtra("population", model.getPopulation());
                intent.putExtra("area", model.getArea());
                intent.putExtra("nativeName", model.getNativeName());

                context.startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        message.setText("Press to request data");

    }
}
