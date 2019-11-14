package com.example.wilson.exampleapi.dao;

import android.content.Context;
import android.util.Log;

import com.example.wilson.exampleapi.async.AsyncRequest;
import com.example.wilson.exampleapi.db.DBManager;
import com.example.wilson.exampleapi.interfaces.MainDAO;
import com.example.wilson.exampleapi.interfaces.ProcessListener;
import com.example.wilson.exampleapi.model.CountryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainDAOImp implements MainDAO {

    private final String url;
    private DBManager manager;
    private ProcessListener mListener;

    public MainDAOImp (Context context) {
        url = "https://restcountries.eu/rest/v2/all";
        manager = new DBManager(context, "DBCountries", null, 1);
    }

    public void setProcessListener(ProcessListener listener) {
        mListener = listener;
    }

    @Override
    public void executeRequest() {
        AsyncRequest r = new AsyncRequest();
        r.setStringUrl(url);
        r.setDAO(this);
        r.execute();
    }

    @Override
    public void onReceivedResponse(Object o) {
        if (mListener != null) {

            JSONArray a = (JSONArray) o;
            ArrayList<CountryModel> countryList = new ArrayList<>();

            for (int i = 0; i < a.length(); i++) {

                CountryModel model = new CountryModel();
                try {
                    JSONObject json = a.getJSONObject(i);

                    model.setName(json.getString("name"));
                    model.setAlpha2Code(json.getString("alpha2Code"));
                    model.setAlpha3Code(json.getString("alpha3Code"));
                    model.setCapital(json.getString("capital"));
                    model.setRegion(json.getString("region"));
                    model.setSubregion(json.getString("subregion"));
                    model.setPopulation(json.getInt("population"));
                    model.setDemonym(json.getString("demonym"));
                    model.setArea(json.getDouble("area"));
                    model.setNativeName(json.getString("nativeName"));
                    model.setNumericCode(json.getString("numericCode"));

                    ArrayList<HashMap> currencies = new ArrayList<>();
                    JSONArray array = json.getJSONArray("currencies");
                    for (int c = 0; c < array.length(); c++) {
                        HashMap<String, String> currency = new HashMap<>();
                        currency.put("code", array.getJSONObject(c).getString("code"));
                        currency.put("name", array.getJSONObject((c)).getString("name"));
                        currency.put("symbol", array.getJSONObject(c).getString("symbol"));

                        currencies.add(currency);
                    }

                    ArrayList<HashMap> languages = new ArrayList<>();
                    JSONArray lgarr = json.getJSONArray("languages");

                    for (int c = 0; c < lgarr.length(); c++){
                        HashMap<String, String> language = new HashMap<>();

                        language.put("iso639_1", lgarr.getJSONObject(c).getString("iso639_1"));
                        language.put("iso639_2", lgarr.getJSONObject(c).getString("iso639_2"));
                        language.put("name", lgarr.getJSONObject(c).getString("name"));
                        language.put("nativeName", lgarr.getJSONObject(c).getString("nativeName"));
                        languages.add(language);
                    }

                    model.setCurrencies(currencies);
                    model.setLanguages(languages);

                    manager.insert(model);
                    countryList.add(model);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error","Something were wrong: "+e);
                }

            }

            mListener.onProcessFinished(countryList);
        }
    }
}
