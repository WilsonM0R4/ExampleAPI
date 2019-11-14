package com.example.wilson.exampleapi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wilson.exampleapi.R;
import com.example.wilson.exampleapi.model.CountryModel;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    private ArrayList<CountryModel> data;
    private View.OnClickListener mListener;

    public CountriesAdapter(ArrayList<CountryModel> data, View.OnClickListener listener) {
        this.data = data;
        mListener = listener;
    }

    public void setOnItemClickListener (View.OnClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_list_item, viewGroup, false);
        itemView.setOnClickListener(mListener);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView capital;
        private TextView population;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.country_name);
            capital = itemView.findViewById(R.id.country_capital);
            population = itemView.findViewById(R.id.country_population);

        }

        public void bindView(CountryModel model){

            name.setText("Country Name: "+model.getName());
            capital.setText("Capital: "+model.getCapital());
            population.setText("Population: "+model.getPopulation());

            Log.e("Item", "Name: "+model.getName()+", capital: "+model.getCapital()+", population: "+model.getPopulation());

        }
    }

}
