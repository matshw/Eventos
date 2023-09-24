package br.mateus.appeventos.Persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DBEvento extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "event_db";
        public static final int DATABASE_VERSION = 1;

        // Tabela de eventos
        public static final String TABLE_EVENTS = "events";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";

        // SQL para criar a tabela de eventos
        private static final String CREATE_TABLE_EVENTS =
                "CREATE TABLE " + TABLE_EVENTS + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_TYPE + " TEXT" +
                        ")";

        public DBEvento(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_EVENTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Caso você precise fazer uma atualização do banco de dados
        }
    }
