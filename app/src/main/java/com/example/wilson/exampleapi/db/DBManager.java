package com.example.wilson.exampleapi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wilson.exampleapi.model.CountryModel;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private String sqlCreate = "create table countries (name TEXT, alpha2code TEXT, alpha3code TEXT," +
            " capital TEXT, region TEXT, subregion TEXT, population INTEGER, area REAL, nativeName TEXT)";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(CountryModel model) {

        String query = "insert into countries values("+model.getName()+","+model.getAlpha2Code()
                +","+model.getAlpha3Code()+","+model.getCapital()+","+model.getRegion()+","
                +model.getSubregion()+","+model.getPopulation()+","+model.getArea()+","
                +model.getNativeName()+")";

        Log.e("Query", "query: "+query);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues  newReister = new ContentValues();

        newReister.put("name", model.getName());
        newReister.put("alpha2code", model.getAlpha2Code());
        newReister.put("alpha3code", model.getAlpha3Code());
        newReister.put("capital", model.getCapital());
        newReister.put("region", model.getRegion());
        newReister.put("subregion", model.getSubregion());
        newReister.put("population", model.getPopulation());
        newReister.put("area", model.getArea());
        newReister.put("nativeName", model.getNativeName());

        if (db!=null) {
            db.insert("countries", null, newReister);
            db.close();
        }
    }

    public ArrayList<CountryModel> read(String name) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "select * from countries";

        if (name!=null && !name.isEmpty()) {
            query = query+" where name = "+name;
        }

        Cursor c = database.rawQuery(query, null);

        ArrayList<CountryModel> list = new ArrayList<>();

        if (c.moveToFirst()) {

            do {

                CountryModel model = new CountryModel();

                model.setName(c.getString(0));
                model.setAlpha2Code(c.getString(1));
                model.setAlpha3Code(c.getString(2));
                model.setCapital(c.getString(3));
                model.setRegion(c.getString(4));
                model.setSubregion(c.getString(5));
                model.setPopulation(c.getInt(6));
                model.setArea(c.getDouble(7));
                model.setNativeName(c.getString(8));

                list.add(model);

            } while (c.moveToNext());
        }

        return list;

    }
}
