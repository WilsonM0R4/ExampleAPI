package com.example.wilson.exampleapi.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wilson.exampleapi.interfaces.MainDAO;
import com.example.wilson.exampleapi.interfaces.ProcessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncRequest extends AsyncTask {

    private String stringUrl;
    private MainDAO mDAO;

    public AsyncRequest () {

    }

    public String getStringUrl() {
        return stringUrl;
    }

    public void setStringUrl(String stringUrl) {
        this.stringUrl = stringUrl;
    }

    public void setDAO (MainDAO dao) {
        mDAO = dao;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        JSONObject obj = null;

        try {
            URL url = new URL(this.stringUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String responseLine = null;

            while ((responseLine = r.readLine())!= null) {

                builder.append(responseLine.trim());

            }

            JSONArray array = new JSONArray(builder.toString());

            return array;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("Error", "JSON exception: "+e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        if (o != null && mDAO!=null) {
            Log.e("Response", ""+o.toString());
            mDAO.onReceivedResponse(o);
        } else {
            Log.e("Response", "Something were wrong");
        }

    }
}
