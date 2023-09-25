package br.mateus.appeventos.Persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DBEvento extends SQLiteOpenHelper {
        public DBEvento(Context context) {
            super(context, "db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql ="CREATE TABLE evento(" +
                    "id integer primary key," +
                    "nome varchar(80)," +
                    "data varchar(40)," +
                    "tipo varchar(40)" +
                    ");";

            sqLiteDatabase.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        }
    }
