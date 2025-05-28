package com.example.conversormoneda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class MonedasDb extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE monedas (moneda TEXT, valor REAL)";
    public MonedasDb (Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO monedas (moneda, valor) VALUES ('COP', 1.0)");
        db.execSQL("INSERT INTO monedas (moneda, valor) VALUES ('USD', 3940.0)");
        db.execSQL("INSERT INTO monedas (moneda, valor) VALUES ('EUR', 4225.0)");
        db.execSQL("INSERT INTO monedas (moneda, valor) VALUES ('MXN', 230.0)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public double getValor(String moneda) {
        SQLiteDatabase db = getReadableDatabase();
        double valor = 0.0;

        String sql = "SELECT valor FROM monedas WHERE moneda = ?";
        Cursor c = db.rawQuery(sql, new String[]{moneda});
        if (c.moveToFirst()) {
            valor = c.getDouble(0);
        }
        c.close();
        db.close();

        return valor;
    }
}


