package com.example.wilson.exampleapi.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.wilson.exampleapi.dao.MainDAOImp;
import com.example.wilson.exampleapi.db.DBManager;
import com.example.wilson.exampleapi.interfaces.MainDAO;
import com.example.wilson.exampleapi.interfaces.ProcessListener;
import com.example.wilson.exampleapi.viewmodel.MainViewModel;

public class MainController implements ProcessListener {

    private final MainDAO mDAO;
    private MainViewModel model;
    private Context context;

    public MainController (Context context) {
        mDAO = new MainDAOImp(context);
        this.context = context;
    }

    public void setViewModel(MainViewModel model) {
        this.model = model;
    }

    public void requestCountryList(){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        //If there is not internet connection, the app will load the data from the database.
        if (info != null && info.isConnected()) {
            ((MainDAOImp)mDAO).setProcessListener(new ProcessListener() {
                @Override
                public void onProcessFinished(Object o) {
                    MainController.this.onProcessFinished(o);
                }
            });
            mDAO.executeRequest();
        } else {
            DBManager dbManager = new DBManager(context, "DBCountries", null, 1);
            this.onProcessFinished(dbManager.read(null));
        }


    }

    @Override
    public void onProcessFinished(Object o) {
        Log.e("Response", "Process is finished. Data: "+o);
        model.onDataUpdate(o);
    }
}
